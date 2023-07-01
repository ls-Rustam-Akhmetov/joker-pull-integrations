package ru.bcs.perseus.bloomberg.config;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.bcs.perseus.bloomberg.service.SoapMessageLoggingService;

@Slf4j
@Configuration
public class BloombergWSConfig {

  @Bean
  public PerSecurityWS perSecurityWS(
      SoapMessageLoggingService soapMessageLoggingService,
      BloombergConfig bloombergConfig
  )
      throws NoSuchAlgorithmException,
      KeyStoreException,
      IOException,
      CertificateException,
      UnrecoverableKeyException,
      KeyManagementException {

    PerSecurityWS_Service service = StringUtils.isNotBlank(bloombergConfig.getAddress()) ?
        new PerSecurityWS_Service(new URL(bloombergConfig.getAddress())) :
        new PerSecurityWS_Service();

    List<Handler> handlerList = new ArrayList<>();
    handlerList.add(soapMessageLoggingService);

    DefaultHandlerResolver defaultHandlerResolver = new DefaultHandlerResolver();
    defaultHandlerResolver.setHandlerList(handlerList);
    service.setHandlerResolver(defaultHandlerResolver);

    PerSecurityWS perSecurityWSPort = service.getPerSecurityWSPort();
    BindingProvider bindingProvider = (BindingProvider) perSecurityWSPort;

    KeyStore ks = KeyStore.getInstance("JKS");
    SSLContext context = SSLContext.getInstance("TLSv1.2");
    char[] keystorePassword;
    try (InputStream inputStream = new FileInputStream(bloombergConfig.getKeystorePath())) {
      keystorePassword = bloombergConfig.getKeystorePassword().toCharArray();
      ks.load(inputStream, keystorePassword);

      KeyManagerFactory keyManagerFactory = KeyManagerFactory
          .getInstance(KeyManagerFactory.getDefaultAlgorithm());
      keyManagerFactory.init(ks, keystorePassword);

      context.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
      bindingProvider.getRequestContext().put(
          "com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory",
          context.getSocketFactory()
      );
      log.info("Ssl has been configured");
    } catch (FileNotFoundException ex) {
      log.warn("No certificate was found");
    }

    return perSecurityWSPort;
  }

  public static class DefaultHandlerResolver implements HandlerResolver {

    private List<Handler> handlerList;

    @Override
    public List<Handler> getHandlerChain(final PortInfo portInfo) {
      return handlerList;
    }

    public void setHandlerList(final List<Handler> handlerList) {
      this.handlerList = handlerList;
    }
  }
}
