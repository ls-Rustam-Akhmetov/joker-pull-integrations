package ru.bcs.perseus.bloomberg.in;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.bcs.perseus.bloomberg.model.db.HistoryQuotesDownloadStatus;
import ru.bcs.perseus.bloomberg.service.HistoryQuoteDownloadStatusService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequestMapping("historyQuoteDownload")
@RestController
@RequiredArgsConstructor
public class HistoryQuoteDownloadStatusController {

    private final HistoryQuoteDownloadStatusService service;

    @GetMapping
    public List<HistoryQuotesDownloadStatus> get(
            @RequestParam(value = "instrumentId", required = false) String instrumentId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        if (instrumentId != null) {
            Optional<HistoryQuotesDownloadStatus> optionalStatus = service.findById(instrumentId);
            return optionalStatus.map(Collections::singletonList).orElse(Collections.emptyList());
        }

        return service.findAll(PageRequest.of(page, size));
    }

    @DeleteMapping
    public void deleteAllByIds(@RequestParam("instrumentId") Set<String> instrumentId) {
        service.deleteAllByIds(instrumentId);
    }

    @PostMapping
    public void save(@RequestBody Set<String> instrumentIdList) {
        instrumentIdList.forEach(service::saveFinishedRecord);
    }
}
