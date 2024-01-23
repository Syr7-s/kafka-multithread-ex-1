package com.syrisa.tr.kafkamultithreadex1.scalable.consumer;

import com.syrisa.tr.kafkamultithreadex1.common.KafkaObject;
import com.syrisa.tr.kafkamultithreadex1.common.KafkaProp;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaScalableWorkerConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaScalableWorkerConsumer.class.getSimpleName());

    private KafkaScalableWorkerConsumer() {
    }

    public static void runWorkerConsumer() {
        Properties kafkaProps = KafkaProp.getProducerProp(KafkaObject.CONSUMER);
        kafkaProps.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 20);
        kafkaProps.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 200);
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

        LOGGER.info("Starting worker threads for parallel processing");
        for (int i = 0; i < 5; i++) {
            Worker worker = new Worker("Worker_" + i);
            Thread newThread = new Thread(worker);
            newThread.start();
        }

        try (kafkaConsumer) {
            kafkaConsumer.subscribe(java.util.List.of("kafka.scalable.products"));
            while (true) {
                kafkaConsumer.poll(java.time.Duration.ofMillis(100))
                        .forEach(data -> {
                            LOGGER.info("Worker Consumer Record Key: " + data.key() + " Record value: " + data.value());
                            Worker.addToQueue(data.value());
                        });
            }
        } catch (WakeupException e) {
            LOGGER.info("Exception caught " + e.getMessage());
        }catch (Exception e) {
            LOGGER.info("Exception caught " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            LOGGER.info("Closing consumer");
            synchronized (currentThread) {
                currentThread.interrupt();
            }
        }
    }
}
