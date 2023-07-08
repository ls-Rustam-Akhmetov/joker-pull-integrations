package ru.example.instruments.service.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.bcs.perseus.instruments.model.Constraint;
import ru.bcs.perseus.instruments.model.Instrument;
import ru.bcs.perseus.instruments.service.InstrumentsService;

import java.util.Collections;
import java.util.List;

@Component
public class ConstraintsCascadeSaveMongoEventListener extends AbstractMongoEventListener<Constraint> {

  @Autowired
  private InstrumentsService instrumentsService;

  @Override
  public void onBeforeConvert(BeforeConvertEvent<Constraint> event) {
    final Constraint constraint = event.getSource();
    List<String> constraintInstrumentId = Collections.singletonList(constraint.getInstrumentId());
    List<Instrument> instruments = instrumentsService.findInstruments(constraintInstrumentId);
    for (Instrument instrument : instruments) {
      instrument.setConstraint(constraint);
    }
    instrumentsService.save(instruments);
  }
}
