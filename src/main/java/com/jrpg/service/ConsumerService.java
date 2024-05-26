package com.jrpg.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumerService {

    private final List<String> processedMessages = new ArrayList<>();

    @KafkaListener(topics = "spaceship_topic", groupId = "jrpg-group-id")
    public void consume(String message) {
    	System.out.println("Consumed message: " + message);
        // process message
        processedMessages.add(message);
    }

    public List<String> getProcessedMessages() {
        return new ArrayList<>(processedMessages);
    }

    public void clearProcessedMessages() {
        processedMessages.clear();
    }
}
