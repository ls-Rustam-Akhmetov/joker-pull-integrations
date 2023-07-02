package ru.bcs.perseus.quotes.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.bcs.perseus.quotes.model.InstrumentType;
import ru.bcs.perseus.quotes.service.QuotesService;
import ru.bcs.perseus.quotes.service.events.QuotesEventGenerator;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

@RestController
@AllArgsConstructor
public class QuotesEventsController extends BaseController {

    private final QuotesService quotesService;
    private final QuotesEventGenerator quotesEventGenerator;

    //todo: add documentation
    @PostMapping("/events/spread")
    @ResponseStatus(HttpStatus.CREATED)
    public void produceEvents(@RequestBody SpreadRequest spreadRequest) {
        List<InstrumentType> types = matchEventType(spreadRequest.getType());
        quotesService
                .getQuotesByTypesOnDate(types, spreadRequest.getDate())
                .forEach(quotesEventGenerator::spreadEvent);
    }

    // todo: move all next to the proper place
    private List<InstrumentType> matchEventType(SpreadType spreadType) {
        switch (spreadType) {
            case SPREAD_CURRENCIES:
                return Collections.singletonList(InstrumentType.CURRENCY);
            case SPREAD_INSTRUMENTS:
                return asList(InstrumentType.BOND, InstrumentType.EQUITY);
            default:
                return asList(InstrumentType.values());
        }
    }

    @Data
    public static class SpreadRequest {
        private LocalDate date = LocalDate.now();
        private SpreadType type = QuotesEventsController.SpreadType.SPREAD_ALL;
    }

    public enum SpreadType {
        SPREAD_ALL,
        SPREAD_CURRENCIES,
        SPREAD_INSTRUMENTS
    }
}
