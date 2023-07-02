package ru.bcs.perseus.quotes.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bcs.perseus.quotes.model.dto.instruments.FoundInstrumentsDTO;
import ru.bcs.perseus.quotes.repository.fallback.InstrumentsRepositoryFallback;

@FeignClient(name = "instruments", fallback = InstrumentsRepositoryFallback.class)
public interface InstrumentsRepository {

  @GetMapping("/api/v1.0/instruments/search")
  FoundInstrumentsDTO getInstrument(@RequestParam(value = "id", required = false) String id);
}
