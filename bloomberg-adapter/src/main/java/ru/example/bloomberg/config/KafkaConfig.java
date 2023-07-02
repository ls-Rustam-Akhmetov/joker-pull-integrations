package ru.example.bloomberg.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    public static final String QUOTES_TOPIC_NAME = "adapter-quotes";
    public static final String INSTRUMENTS_TOPIC_NAME = "adapter-instruments";
    public static final String ACTIONS_TOPIC_NAME = "adapter-actions";
    public static final String HISTORY_TOPIC_NAME = "adapter-history";

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic adapterQuotes() {
        return new NewTopic(QUOTES_TOPIC_NAME, 3, (short) 3);
    }

    @Bean
    public NewTopic adapterInstruments() {
        return new NewTopic(INSTRUMENTS_TOPIC_NAME, 3, (short) 3);
    }

    @Bean
    public NewTopic adapterActions() {
        return new NewTopic(ACTIONS_TOPIC_NAME, 3, (short) 3);
    }

    @Bean
    public NewTopic adapterHistory() {
        return new NewTopic(HISTORY_TOPIC_NAME, 3, (short) 3);
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}