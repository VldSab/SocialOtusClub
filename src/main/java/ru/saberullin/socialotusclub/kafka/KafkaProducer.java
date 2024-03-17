package ru.saberullin.socialotusclub.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducer {

    @Value("${kafka.topic-name}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<String, String>> produce(String message, List<Header> headers) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, Instant.now().toEpochMilli(), null, message, headers);
        return kafkaTemplate.send(record);
    }
}
