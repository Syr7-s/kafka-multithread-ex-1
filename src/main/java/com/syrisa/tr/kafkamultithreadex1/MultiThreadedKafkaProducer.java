package com.syrisa.tr.kafkamultithreadex1;

import org.apache.kafka.clients.producer.*;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedKafkaProducer {
    public static void main(String[] args) {
        // Create Kafka producer configuration
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Create Kafka producer instance
        Producer<String, String> producer = new KafkaProducer<>(properties);

        // Define the number of threads
        int numThreads = 5; // Change this value as per your requirement

        // Create ExecutorService
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        // Define the producer task
        Runnable producerTask = () -> {
            // Your message production logic goes here
            // Use the producer instance to send messages to Kafka
            // Example:
            String topic = "multithread.first.ex";
            String key = "Multithreaded Kafka Producer";
            String value = "This is a message from a multithreaded Kafka producer";
            System.out.println("Sending message: " + value+ " from thread: " + Thread.currentThread().getName()+" in time "+ LocalDateTime.now());
            producer.send(new ProducerRecord<>(topic, key, value), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        // Handle the exception
                    } else {
                        // Message sent successfully
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Message sent successfully: " + metadata.topic() + " " + metadata.partition() + " " + metadata.offset());
                    }
                }
            });

        };

        // Submit producer tasks to the ExecutorService
        for (int i = 0; i < numThreads; i++) {
            executorService.submit(producerTask);

        }

        // Shutdown the ExecutorService after all tasks are completed
        executorService.shutdown();

        // Close the Kafka producer
        producer.close();
    }
}