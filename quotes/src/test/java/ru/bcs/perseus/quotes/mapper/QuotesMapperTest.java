package ru.bcs.perseus.quotes.mapper;

import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import ru.bcs.perseus.quotes.model.InstrumentType;
import ru.bcs.perseus.quotes.model.dto.QuoteDto;
import ru.bcs.perseus.quotes.model.dto.QuoteEventDto;
import ru.bcs.perseus.quotes.model.quotes.Exchange;
import ru.bcs.perseus.quotes.model.quotes.Quote;


public class QuotesMapperTest {

  private QuotesMapper quotesMapper = Mappers.getMapper(QuotesMapper.class);

  private final Quote quote = new Quote();

  @Before
  public void setUp() {
    quote.setId("1");
    quote.setInstrumentId("AGD");
    quote.setExchange(Exchange.RX);
    quote.setSource("quik");
    quote.setIsin("100");
    quote.setUser("mama");
    quote.setType(InstrumentType.BOND);
    quote.setTicker("awetg");
    quote.setCurrency("percent");
    quote.setBid(valueOf(10));
    quote.setAsk(valueOf(11));
    quote.setLast(valueOf(12));
    quote.setClose(valueOf(13));
    quote.setOpen(valueOf(14));
    quote.setMax(valueOf(15));
    quote.setMin(valueOf(16));
    quote.setStrike(valueOf(17));
    quote.setBidYTW(valueOf(18));
    quote.setAskYTW(valueOf(19));
    quote.setDate(LocalDate.now());
    quote.setTime(LocalTime.now());
    quote.setCouponPrevFloat("prev");
    quote.setDuration(valueOf(20));
    quote.setCoupon(valueOf(21));
    quote.setCouponFrequency(valueOf(22));
    quote.setCouponNextDate(LocalDate.now());
    quote.setDividendExDate(LocalDate.now());
    quote.setDividendRecordDate(LocalDate.now());
    quote.setDividendShareLast(valueOf(23));
    quote.setDividendCashGrossNext(valueOf(24));
    quote.setDividendCurrencyNext("currency");
    quote.setDividendStockTypeNext("type");
    quote.setDividendStockAdjustFactor("stock");
    quote.setDividendSplitAdjustFactor("split");
    quote.setDividendCashRecordDateNext(LocalDate.now());
    quote.setDividendStockRecordDateNext(LocalDate.now());
    quote.setDividendSplitRecordDate(LocalDate.now());
    quote.setDividendExFlag(true);
    quote.setDividendStockFlag(true);
    quote.setStockSplitFlag(true);
    quote.setAccruedInterest(valueOf(1.2));
    quote.setNominal(valueOf(1000));
    quote.setCouponPrevDate(LocalDate.now());
    quote.setAccruedInterestTradeDate(valueOf(300));
    quote.setPrincipalFactor(valueOf(1));
    quote.setSinkableParMarket("PAR");
    quote.setDefaulted(true);
  }

  @Test
  public void mapToDto() {

    QuoteDto quoteDto = quotesMapper.mapQuote(quote);

    assertEquals(quote.getInstrumentId(), quoteDto.getInstrumentId());
    assertEquals(quote.getExchange(), quoteDto.getExchange());
    assertEquals(quote.getIsin(), quoteDto.getIsin());
    assertEquals(quote.getType(), quoteDto.getType());
    assertEquals(quote.getTicker(), quoteDto.getTicker());
    assertEquals(quote.getBid(), quoteDto.getBid());
    assertEquals(quote.getAsk(), quoteDto.getAsk());
    assertEquals(quote.getLast(), quoteDto.getLast());
    assertEquals(quote.getClose(), quoteDto.getClose());
    assertEquals(quote.getOpen(), quoteDto.getOpen());
    assertEquals(quote.getMax(), quoteDto.getMax());
    assertEquals(quote.getMin(), quoteDto.getMin());
    assertEquals(quote.getStrike(), quoteDto.getStrike());
    assertEquals(quote.getBidYTW(), quoteDto.getBidYTW());
    assertEquals(quote.getAskYTW(), quoteDto.getAskYTW());
    assertEquals(quote.getCurrency(), quoteDto.getCurrency());
    assertEquals(quote.getCouponPrevFloat(), quoteDto.getCouponPrevFloat());
    assertEquals(quote.getDuration(), quoteDto.getDuration());
    assertEquals(quote.getCoupon(), quoteDto.getCoupon());
    assertEquals(quote.getCouponFrequency(), quoteDto.getCouponFrequency());
    assertEquals(quote.getCouponNextDate(), quoteDto.getCouponNextDate());
    assertEquals(quote.getDividendExDate(), quoteDto.getDividendExDate());
    assertEquals(quote.getDividendRecordDate(), quoteDto.getDividendRecordDate());
    assertEquals(quote.getDividendShareLast(), quoteDto.getDividendShareLast());
    assertEquals(quote.getDividendCashGrossNext(), quoteDto.getDividendCashGrossNext());
    assertEquals(quote.getDividendCurrencyNext(), quoteDto.getDividendCurrencyNext());
    assertEquals(quote.getDividendStockTypeNext(), quoteDto.getDividendStockTypeNext());
    assertEquals(quote.getDividendStockAdjustFactor(), quoteDto.getDividendStockAdjustFactor());
    assertEquals(quote.getDividendSplitAdjustFactor(), quoteDto.getDividendSplitAdjustFactor());
    assertEquals(quote.getDividendCashRecordDateNext(), quoteDto.getDividendCashRecordDateNext());
    assertEquals(quote.getDividendStockRecordDateNext(), quoteDto.getDividendStockRecordDateNext());
    assertEquals(quote.getDividendSplitRecordDate(), quoteDto.getDividendSplitRecordDate());
    assertEquals(quote.getDividendExFlag(), quoteDto.getDividendExFlag());
    assertEquals(quote.getDividendStockFlag(), quoteDto.getDividendStockFlag());
    assertEquals(quote.getStockSplitFlag(), quoteDto.getStockSplitFlag());
    assertEquals(quote.getAccruedInterest(), quoteDto.getAccruedInterest());
    assertEquals(quote.getNominal(), quoteDto.getNominal());
    assertEquals(quote.getCouponPrevDate(), quoteDto.getCouponPrevDate());
    assertEquals(quote.getAccruedInterestTradeDate(), quoteDto.getAccruedInterestTradeDate());
    assertEquals(quote.getPrincipalFactor(), quoteDto.getPrincipalFactor());
    assertEquals(quote.getSinkableParMarket(), quoteDto.getSinkableParMarket());
    assertEquals(quote.getDefaulted(), quoteDto.getDefaulted());
  }

  @Test
  public void mapToQuoteEvent() {
    final QuoteEventDto quoteEventDto = quotesMapper.mapQuoteEvent(quote);
    assertEquals(LocalDateTime.of(quote.getDate(), quote.getTime()), quoteEventDto.getTimestamp());
  }


}
