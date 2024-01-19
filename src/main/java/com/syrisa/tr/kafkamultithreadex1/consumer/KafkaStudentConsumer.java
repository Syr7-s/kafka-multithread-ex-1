package com.syrisa.tr.kafkamultithreadex1.consumer;

import com.syrisa.tr.kafkamultithreadex1.common.KafkaObject;
import com.syrisa.tr.kafkamultithreadex1.common.KafkaProp;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Logger;

public class KafkaStudentConsumer {

    private KafkaStudentConsumer() {
    }

    private static final Logger LOGGER = Logger.getLogger(KafkaStudentConsumer.class.getName());

    public static void runConsumer() {
        Properties consumerProperties = KafkaProp.getProducerProp(KafkaObject.CONSUMER);
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(consumerProperties);
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
            kafkaConsumer.subscribe(Arrays.asList("kafka.mthread.students"));
            while (true) {
                kafkaConsumer.poll(Duration.ofMillis(100)).forEach(studentRegister -> LOGGER.info("Message fetched : " + studentRegister));
                kafkaConsumer.commitAsync();
            }
        } catch (WakeupException e) {
            LOGGER.info("Exception caught " + e.getMessage());
        } catch (Exception e) {
            LOGGER.info("Exception caught " + e.getMessage());
            /* Clean up whatever needs to be handled before interrupting  */
            Thread.currentThread().interrupt();
        } finally {
            LOGGER.info("Closing consumer");
            synchronized (currentThread) {
                currentThread.interrupt();
            }
        }

    }
}
