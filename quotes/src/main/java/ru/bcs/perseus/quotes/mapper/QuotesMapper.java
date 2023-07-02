package ru.bcs.perseus.quotes.mapper;

import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.bcs.perseus.quotes.model.dto.QuoteDto;
import ru.bcs.perseus.quotes.model.dto.QuoteEventDto;
import ru.bcs.perseus.quotes.model.quotes.Quote;

@Mapper(componentModel = "spring")
public interface QuotesMapper {

  QuoteDto mapQuote(Quote quote);

  List<QuoteDto> mapAllToDto(List<Quote> quotes);

  QuoteEventDto mapQuoteEvent(Quote quote);

  @BeforeMapping
  default void afterMapping(@MappingTarget QuoteEventDto target, Quote source) {
    target.setTimestamp(LocalDateTime.of(source.getDate(), source.getTime()));
  }
}
