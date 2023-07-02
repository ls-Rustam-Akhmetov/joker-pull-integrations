package ru.bcs.perseus.bloomberg.config;


import com.bloomberg.services.dlws.PerSecurityWS;
import com.bloomberg.services.dlws.PerSecurityWS_Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Slf4j
@Configuration
public class BloombergWSConfig {

    @Bean
    public PerSecurityWS perSecurityWS(BloombergConfig bloombergConfig) throws IOException {

        PerSecurityWS_Service service = StringUtils.isNotBlank(bloombergConfig.getAddress()) ?
                new PerSecurityWS_Service(new URL(bloombergConfig.getAddress())) :
                new PerSecurityWS_Service();

        return service.getPerSecurityWSPort();
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
