package ru.example.instruments.service.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.bcs.perseus.instruments.model.Instrument;
import ru.bcs.perseus.instruments.model.Proxy;
import ru.bcs.perseus.instruments.service.InstrumentsService;

import java.util.Collections;
import java.util.List;

@Component
public class ProxiesCascadeSaveMongoEventListener extends AbstractMongoEventListener<Proxy> {

  @Autowired
  private InstrumentsService instrumentsService;

  @Override
  public void onBeforeConvert(BeforeConvertEvent<Proxy> event) {
    final Proxy proxy = event.getSource();
    List<String> proxyInstrumentId = Collections.singletonList(proxy.getInstrumentId());
    List<Instrument> instruments = instrumentsService.findInstruments(proxyInstrumentId);
    for (Instrument instrument : instruments) {
      instrument.setProxy(proxy);
    }
    instrumentsService.save(instruments);
  }
}
