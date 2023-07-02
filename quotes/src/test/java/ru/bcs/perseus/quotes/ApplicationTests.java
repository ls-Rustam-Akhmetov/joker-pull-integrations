package ru.bcs.perseus.quotes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bcs.perseus.quotes.service.events.QuotesListener;

@RunWith(SpringRunner.class)
@SpringBootTest
@MockBean(classes = {QuotesListener.class})
@ActiveProfiles("test")
public class ApplicationTests {

    @Mock
    RabbitAutoConfiguration rabbitAutoConfiguration;

    @Test
    @SuppressWarnings("squid:S2699")
    public void contextLoads() {
    }
}