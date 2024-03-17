package ru.saberullin.socialotusclub.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import ru.saberullin.socialotusclub.feed.Feed;

@Service
public class KafkaTopicListener {
    @Value("${kafka.topic-name}")
    private String topic;

    private final Feed feed;

    public KafkaTopicListener(Feed feed) {
        this.feed = feed;
    }

    @KafkaListener(topics = "${kafka.topic-name}")
    public void listen(ConsumerRecord<String, String> record, Acknowledgment ack) {
        feed.addPostToFeed(record);
        System.out.println(record.value() + "\n");
        ack.acknowledge();
    }

}
