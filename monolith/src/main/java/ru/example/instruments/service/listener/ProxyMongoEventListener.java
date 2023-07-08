package ru.example.instruments.service.listener;

import io.micrometer.core.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;
import ru.bcs.perseus.instruments.config.CustomBinding;
import ru.bcs.perseus.instruments.model.Proxy;

import static org.springframework.messaging.support.MessageBuilder.withPayload;

@Slf4j
@Component
@AllArgsConstructor
public class ProxyMongoEventListener extends AbstractMongoEventListener<Proxy> {

  private final CustomBinding binding;

  @Override
  public void onAfterSave(@NonNull AfterSaveEvent<Proxy> event) {
    final Proxy proxy = event.getSource();
    // Distribute event
    binding.proxiesChannel().send(withPayload(proxy).build());
    log.debug("Distribute event {}", proxy);

    super.onAfterSave(event);
  }
}
