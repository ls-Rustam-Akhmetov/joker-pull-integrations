package ru.example.bloomberg.out.ws;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.example.bloomberg.model.Response;
import ru.example.bloomberg.model.db.RequestType;
import ru.example.bloomberg.model.db.Sync;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@AllArgsConstructor
public class BloombergAdapterV2 {

    private static final int MAX_ATTEMPTS = 3;
    private final ScheduledExecutorService scheduler;
    private final BloombergAdapter oldBloomberAdapter;

    public CompletableFuture<Response> getBloombergData(List<Sync> syncList, RequestType requestType) {
        CompletableFuture<Response> future = new CompletableFuture<>();

        // Make the initial requestData call
        requestForDataPreparation(syncList, requestType)
                .thenCompose(requestId -> acquireData(requestId, future, 0))
                .exceptionally(ex -> {
                    future.completeExceptionally(ex);
                    return null;
                });

        return future;
    }

    private CompletableFuture<Response> acquireData(String bloombergRequestId, CompletableFuture<Response> future, int counter) {
        Response response = oldBloomberAdapter.requestForDataAcquiring(bloombergRequestId);
        if (response.getStatus() == Response.Status.DATA_PREPARATION_IN_PROGRESS && counter < MAX_ATTEMPTS) {
            final int addedCounter = counter + 1;
            scheduler.schedule(() -> acquireData(bloombergRequestId, future, addedCounter), 5, TimeUnit.MINUTES);
        } else {
            future.complete(response);
        }
        return future;
    }

    private CompletableFuture<String> requestForDataPreparation(List<Sync> syncList, RequestType requestType) {
        String requestId = oldBloomberAdapter.requestForDataPreparation(syncList, requestType);
        return CompletableFuture.completedFuture(requestId);
    }
}
