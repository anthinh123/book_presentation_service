package com.thinh.bookpresentationservice.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.thinh.bookpresentationservice.model.BookViewedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class BookViewedKafkaProducer {

    private static final String BOOK_VIEWED_TOPIC = "book_viewed_topic";

    @Autowired
    private KafkaTemplate<Long, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public BookViewedKafkaProducer() {
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }


    // Overload for sending without a key
    public void sendMessage(long bookId, String userId) {
        try {
            BookViewedEvent event = BookViewedEvent.builder()
                    .bookId(bookId)
                    .userId(userId)
                    .timestamp(Instant.now().toString())
                    .build();
            String message = objectMapper.writeValueAsString(event);
            System.out.printf("Sending message to topic %s: %s%n", BOOK_VIEWED_TOPIC, message);
            kafkaTemplate.send(BOOK_VIEWED_TOPIC, bookId, message);
        } catch (Exception e) {
            System.err.println("Error sending BookViewed event: " + e.getMessage());
        }
    }

}
