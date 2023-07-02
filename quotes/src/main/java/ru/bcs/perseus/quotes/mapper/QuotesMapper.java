package ru.bcs.perseus.quotes.mapper;

import org.mapstruct.Mapper;
import ru.bcs.perseus.quotes.model.dto.QuoteDto;
import ru.bcs.perseus.quotes.model.quotes.Quote;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuotesMapper {

    QuoteDto mapQuote(Quote quote);

    List<QuoteDto> mapAllToDto(List<Quote> quotes);
}
