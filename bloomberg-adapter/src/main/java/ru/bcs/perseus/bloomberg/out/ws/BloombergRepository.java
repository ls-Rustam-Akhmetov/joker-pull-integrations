package ru.bcs.perseus.bloomberg.out.ws;

import static java.util.stream.Collectors.toList;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.PX_LAST;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.YLD_CNV_MID;
import static ru.bcs.perseus.bloomberg.out.ws.helper.BloombergRequestHelper.makeHistoryInstrument;
import static ru.bcs.perseus.bloomberg.out.ws.helper.QuoteHelper.extractHistoryQuote;

import com.bloomberg.services.dlws.DateRange;
import com.bloomberg.services.dlws.Duration;
import com.bloomberg.services.dlws.Fields;
import com.bloomberg.services.dlws.GetHistoryHeaders;
import com.bloomberg.services.dlws.InstrumentData;
import com.bloomberg.services.dlws.Instruments;
import com.bloomberg.services.dlws.PerSecurityWS;
import com.bloomberg.services.dlws.ResponseStatus;
import com.bloomberg.services.dlws.RetrieveGetDataRequest;
import com.bloomberg.services.dlws.RetrieveGetDataResponse;
import com.bloomberg.services.dlws.RetrieveGetHistoryRequest;
import com.bloomberg.services.dlws.RetrieveGetHistoryResponse;
import com.bloomberg.services.dlws.SubmitGetDataRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import jakarta.xml.ws.Holder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.bcs.perseus.bloomberg.model.Response;
import ru.bcs.perseus.bloomberg.model.db.RequestType;
import ru.bcs.perseus.bloomberg.model.db.Sync;
import ru.bcs.perseus.bloomberg.model.instrument.Dividend;
import ru.bcs.perseus.bloomberg.model.instrument.Instrument;
import ru.bcs.perseus.bloomberg.model.instrument.InstrumentV2;
import ru.bcs.perseus.bloomberg.model.quote.Quote;
import ru.bcs.perseus.bloomberg.out.ws.helper.BloombergRequestHelper;
import ru.bcs.perseus.bloomberg.out.ws.helper.ResponseParser;

@Slf4j
@Repository
@AllArgsConstructor
public class BloombergRepository {

  private static final int DATA_READY_STATUS = 0;
  private static final int DATA_PROCESSING_IN_PROGRESS_STATUS = 100;

  private final PerSecurityWS perSecurityWS;

  /**
   * retrieve data from bloomber by request id
   */
  public Response requestForDataRetrieval(String bloombergRequestId) {
    RetrieveGetDataResponse response = retrieveResponse(bloombergRequestId);
    int responseStatusCode = response.getStatusCode().getCode();
    Response.Status responseStatus = makeResponseStatus(responseStatusCode);
    return makeResponse(response, responseStatus);
  }

  /**
   * retrieve history data from bloomber by request id
   */
  public Response requestForQuotesHistoryDataRetrieval(String bloombergRequestId, Sync sync) {
    RetrieveGetHistoryRequest request = new RetrieveGetHistoryRequest();
    request.setResponseId(bloombergRequestId);

    RetrieveGetHistoryResponse response = perSecurityWS.retrieveGetHistoryResponse(request);
    int responseStatusCode = response.getStatusCode().getCode();
    Response.Status responseStatus = makeResponseStatus(responseStatusCode);
    List<Quote> quotes = Collections.emptyList();
    if (Response.Status.DATA_READY == responseStatus) {
      quotes = response.getInstrumentDatas()
          .getInstrumentData()
          .stream()
          .map(instrument -> extractHistoryQuote(instrument, sync))
          .filter(Objects::nonNull)
          .collect(toList());
    }

    return Response.builder()
        .status(responseStatus)
        .quotes(quotes)
        .build();
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

  /**
   * @return request_id
   */
  public String requestInstrumentsQuotesHistory(Sync sync, int periodInDays) {
    Instruments instruments = new Instruments();
    instruments.getInstrument().add(makeHistoryInstrument(sync));

    GetHistoryHeaders headers = new GetHistoryHeaders();
    DateRange dateRange = new DateRange();
    Duration duration = new Duration();
    duration.setDays(periodInDays);
    dateRange.setDuration(duration);
    headers.setDaterange(dateRange);

    Fields fields = new Fields();

    fields.getField().add(YLD_CNV_MID);
    fields.getField().add(PX_LAST);

    Holder<ResponseStatus> statusCodeHolder = new Holder<>();
    Holder<String> requestIdHolder = new Holder<>();
    Holder<String> responseIdHolder = new Holder<>();

    perSecurityWS.submitGetHistoryRequest(
        headers,
        fields,
        instruments,
        statusCodeHolder,
        requestIdHolder,
        responseIdHolder
    );

    return responseIdHolder.value;
  }

  private Response makeResponse(
      RetrieveGetDataResponse response,
      Response.Status responseStatus
  ) {

    List<Quote> quotes = Collections.emptyList();
    List<Instrument> instruments = Collections.emptyList();
    List<InstrumentV2> instrumentsV2 = Collections.emptyList();
    List<Dividend> dividends = Collections.emptyList();

    if (responseStatus == Response.Status.DATA_READY && response.getFields() != null) {
      List<InstrumentData> instrumentDataList = response.getInstrumentDatas().getInstrumentData();
      List<String> fields = response.getFields().getField();
      quotes = ResponseParser.retrieveQuotes(instrumentDataList, fields);
      instruments = ResponseParser.retrieveInstruments(instrumentDataList, fields);
      instrumentsV2 = ResponseParser.retrieveInstrumentsV2(instrumentDataList, fields);
      dividends = ResponseParser.retrieveDividends(instrumentDataList);
    }

    return Response.builder()
        .status(responseStatus)
        .instruments(instruments)
        .quotes(quotes)
        .dividends(dividends)
        .build();
  }

  private RetrieveGetDataResponse retrieveResponse(String responseId) {
    RetrieveGetDataRequest retrieveGetDataRequest = new RetrieveGetDataRequest();
    retrieveGetDataRequest.setResponseId(responseId);

    return perSecurityWS.retrieveGetDataResponse(retrieveGetDataRequest);
  }

  private Response.Status makeResponseStatus(int statusCode) {

    switch (statusCode) {
      case DATA_READY_STATUS:
        return Response.Status.DATA_READY;
      case DATA_PROCESSING_IN_PROGRESS_STATUS:
        return Response.Status.DATA_PREPARATION_IN_PROGRESS;
      default:
        log.warn("Unknown response status :{}", statusCode);
        return Response.Status.UNKNOWN;
    }
  }
}
