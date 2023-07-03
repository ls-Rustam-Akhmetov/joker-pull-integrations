package ru.example.bloomberg.out.ws;

import com.bloomberg.services.dlws.*;
import jakarta.xml.ws.Holder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.example.bloomberg.model.Response;
import ru.example.bloomberg.model.db.RequestType;
import ru.example.bloomberg.model.db.Sync;
import ru.example.bloomberg.model.instrument.Dividend;
import ru.example.bloomberg.model.instrument.Instrument;
import ru.example.bloomberg.model.quote.Quote;
import ru.example.bloomberg.out.ws.helper.BloombergRequestHelper;
import ru.example.bloomberg.out.ws.helper.ResponseParser;

import java.util.Collections;
import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class BloombergAdapter {

    private static final int DATA_READY_STATUS = 0;
    private static final int DATA_PROCESSING_IN_PROGRESS_STATUS = 100;

    private final PerSecurityWS perSecurityWS;

    /**
     * retrieve data from bloomber by request id
     */
    public Response requestForDataRetrieval(String bloombergRequestId) {
        RetrieveGetDataResponse response = makeRequestForData(bloombergRequestId);
        int responseStatusCode = response.getStatusCode().getCode();
        Response.Status responseStatus = makeResponseStatus(responseStatusCode);
        return makeResponse(response, responseStatus);
    }

    /**
     * @param requestType RequestType[INSTRUMENT,QUOTE,COMBINED]
     * @return request_id
     */
    public String requestForDataPreparation(List<Sync> syncList, RequestType requestType) {
        SubmitGetDataRequest request = BloombergRequestHelper
                .makeSubmitGetDataRequest(syncList, requestType);
        Holder<ResponseStatus> statusCodeHolder = new Holder<>();
        Holder<String> requestIdHolder = new Holder<>();
        Holder<String> responseIdHolder = new Holder<>();
        perSecurityWS.submitGetDataRequest(
                request.getHeaders(),
                null,
                request.getFields(),
                request.getInstruments(),
                statusCodeHolder,
                requestIdHolder,
                responseIdHolder
        );

        return responseIdHolder.value;
    }

    private Response makeResponse(RetrieveGetDataResponse response, Response.Status responseStatus) {

        List<Quote> quotes = Collections.emptyList();
        List<Instrument> instruments = Collections.emptyList();
        List<Dividend> dividends = Collections.emptyList();

        if (responseStatus == Response.Status.DATA_READY && response.getFields() != null) {
            List<InstrumentData> instrumentDataList = response.getInstrumentDatas().getInstrumentData();
            List<String> fields = response.getFields().getField();
            quotes = ResponseParser.retrieveQuotes(instrumentDataList, fields);
            instruments = ResponseParser.retrieveInstruments(instrumentDataList, fields);
            dividends = ResponseParser.retrieveDividends(instrumentDataList);
        }

        return Response.builder()
                .status(responseStatus)
                .instruments(instruments)
                .quotes(quotes)
                .dividends(dividends)
                .build();
    }

    private RetrieveGetDataResponse makeRequestForData(String responseId) {
        RetrieveGetDataRequest retrieveGetDataRequest = new RetrieveGetDataRequest();
        retrieveGetDataRequest.setResponseId(responseId);

        return perSecurityWS.retrieveGetDataResponse(retrieveGetDataRequest);
    }

    private Response.Status makeResponseStatus(int statusCode) {

        return switch (statusCode) {
            case DATA_READY_STATUS -> Response.Status.DATA_READY;
            case DATA_PROCESSING_IN_PROGRESS_STATUS -> Response.Status.DATA_PREPARATION_IN_PROGRESS;
            default -> Response.Status.UNKNOWN;
        };
    }
}
