package ru.example.quotes.mapper;

import org.mapstruct.Mapper;
import ru.example.quotes.model.dto.QuoteDto;
import ru.example.quotes.model.quotes.Quote;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuotesMapper {

    QuoteDto mapQuote(Quote quote);

    List<QuoteDto> mapAllToDto(List<Quote> quotes);
}
