package ru.bcs.perseus.quotes.config;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("ru.bcs.perseus.quotes.repository")
public class MongoTestConfiguration extends AbstractMongoConfiguration {

    /**
     * Return the name of the database to connect to.
     *
     * @return must not be {@literal null}.
     */
    @Override
    protected String getDatabaseName() {
        return "test";
    }

    /**
     * Return the {@link Mongo} instance to connect to. Annotate with {@link Bean} in case you want to expose a
     * {@link Mongo} instance to the {@link ApplicationContext}.
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public MongoClient mongoClient() {
        Fongo fongo = new Fongo("test");
        return fongo.getMongo();
    }
}
