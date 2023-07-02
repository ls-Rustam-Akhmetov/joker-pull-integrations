package ru.example.quotes.out.db;

import com.mongodb.client.DistinctIterable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class QuotesMongoTemplateRepository {

    private static final boolean USE_PARALLEL_STREAM = true;

    private final MongoTemplate mongoTemplate;

    public Stream<String> getDistinctInstrumentIds() {
        DistinctIterable<String> distinctIterable = mongoTemplate.getCollection("quotes")
                .distinct("instrumentId", String.class);
        return StreamSupport.stream(distinctIterable.spliterator(), USE_PARALLEL_STREAM);
    }
}
