package com.syrisa.tr.kafkamultithreadex1.scalable.producer;

import com.syrisa.tr.kafkamultithreadex1.common.KafkaObject;
import com.syrisa.tr.kafkamultithreadex1.common.KafkaProp;
import com.syrisa.tr.kafkamultithreadex1.producer.KafkaMultithreadingStudentProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaScalableStudentProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMultithreadingStudentProducer.class.getSimpleName());


    private KafkaScalableStudentProducer() {
    }

    public static void runScalableProducer() {
        Properties kafkaScalableProp = KafkaProp.getProducerProp(KafkaObject.PRODUCER);
        kafkaScalableProp.setProperty("batch.size", "32768");
        kafkaScalableProp.setProperty("acks", "all");
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(kafkaScalableProp);
        try (kafkaProducer) {
            AutoProductGenerateHouse.getProducerRecord().forEach(
                    product -> kafkaProducer.send(product, (recordMetadata, e) -> {
                        if (e != null) {
                            LOGGER.error("No data in queue");
                        } else {
                            LOGGER.info("Record sent successfully to Kafka - Record Metadata:- " +
                                    " Topic: " + recordMetadata.topic() + " Partition:" + recordMetadata.partition() + " Key:" + product.key() +
                                    " Value: " + product.value() +
                                    " Offset: " + recordMetadata.offset() +
                                    " Timestamp: " + recordMetadata.timestamp());
                        }
                    })
            );
        } catch (Exception e) {
            LOGGER.error("Error while producing", e);
        }

    }
}
