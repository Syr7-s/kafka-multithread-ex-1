package com.syrisa.tr.kafkamultithreadex1.scalable.consumer;

import com.syrisa.tr.kafkamultithreadex1.common.KafkaObject;
import com.syrisa.tr.kafkamultithreadex1.common.KafkaProp;
import com.syrisa.tr.kafkamultithreadex1.producer.KafkaMultithreadingStudentProducer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class KafkaScalableConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMultithreadingStudentProducer.class.getSimpleName());

    private KafkaScalableConsumer() {
    }

    public static void runConsumer() {
        Properties kafkaProps = KafkaProp.getProducerProp(KafkaObject.CONSUMER);
        /* Batch Parameters
        ConsumerConfig.FETCH_MIN_BYTES_CONFIG : Minimum number of bytes to fetch from Kafka. Default is 1 byte.
        ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG : Maximum wait time for a fetch request to Kafka. Default is 500 ms.
        ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG : Maximum number of bytes to fetch from a single partition in a fetch request. Default is 1 MB.
         */

        kafkaProps.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 10);
        kafkaProps.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 100);
        kafkaProps.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 2097152);

        /*Auto Commit Parameters
        What is auto commit?
        Auto commit is a feature that allows the consumer to commit the offset of the last message read from Kafka.
        kafkaProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); : Set auto commit to false
        */

        kafkaProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(kafkaProps);
        final Thread currentThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Caught shutdown hook");
            kafkaConsumer.wakeup();
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                LOGGER.info("Exception caught " + e.getMessage());
                /* Clean up whatever needs to be handled before interrupting  */
                Thread.currentThread().interrupt();
            }
        }));
        try (kafkaConsumer) {
            kafkaConsumer.subscribe(List.of("kafka.scalable.products"));
            AtomicInteger recordCount = new AtomicInteger();
            while (true) {
                ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(100));
                consumerRecords.forEach(product -> {
                    recordCount.getAndIncrement();
                    LOGGER.info("Record Key: " + product.key() + " Record value: " + product.value() +
                            " Record partition: " + product.partition() + " Record offset: " + product.offset());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                if (recordCount.get() % 1000 == 0) {
                    LOGGER.info("Committing offset");
                    kafkaConsumer.commitAsync();
                }
            }
        } catch (WakeupException exception) {
            LOGGER.info("Exception caught " + exception.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error while consuming", e);
        }
    }
}
