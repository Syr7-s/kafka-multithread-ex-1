package com.syrisa.tr.kafkamultithreadex1.producer;

import com.syrisa.tr.kafkamultithreadex1.common.CustomThreadFactory;
import com.syrisa.tr.kafkamultithreadex1.common.KafkaObject;
import com.syrisa.tr.kafkamultithreadex1.common.KafkaProp;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class KafkaMultithreadingStudentProducer {

    private KafkaMultithreadingStudentProducer() {
    }

    private static final Random RANDOM = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMultithreadingStudentProducer.class.getSimpleName());


    public static void runProducer() {
        Properties kafkaProp = KafkaProp.getProducerProp(KafkaObject.PRODUCER);
        ThreadFactory threadFactory = new CustomThreadFactory("KafkaProducer");
        ExecutorService executorService = Executors.newFixedThreadPool(5, threadFactory);
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(kafkaProp);

        List<String> letterGrade = List.of("A", "B", "C", "D", "F");
        LocalDateTime startTime = LocalDateTime.now();
        List<ProducerRecord<String, String>> students = new ArrayList<>();
        try (executorService; kafkaProducer) {
            int startKey = RANDOM.nextInt(5000);
            TimeUnit.SECONDS.sleep(5);
            for (int i = 1; i < startKey + 10; i++) {
//                executorService.execute(new KafkaProduceData(kafkaProducer,
//                        new ProducerRecord<>(
//                                "kafka.mthread.students",
//                                String.valueOf(RANDOM.nextInt(1000, 9999)) + "numbered student",
//                                "Student " + i + " 's letter grade = '" + letterGrade.get(RANDOM.nextInt(letterGrade.size()))
//                        )));
                ProducerRecord<String, String> produceStudent =
                        new ProducerRecord<>("kafka.mthread.students",
                                String.valueOf(RANDOM.nextInt(1000, 9999)) + " numbered student ",
                                "Student " + i + " 's letter grade = '" + letterGrade.get(RANDOM.nextInt(letterGrade.size())));

                students.add(produceStudent);
            }
            if (students == null) {
                LOGGER.error("Error while producing", new Exception("Records is null"));
            } else {
                students.forEach(student -> {
                    kafkaProducer.send(student, new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata metadata, Exception exception) {
                            // executes every time a record is successfully sent or an exception is thrown
                            if (exception == null) {
                                // the record was successfully sent
                                LOGGER.info(
                                        "Key: " + student.key() +
                                                " Value: " + student.value() +
                                                " Topic: " + metadata.topic() +
                                                " Partition: " + metadata.partition());
                            } else {
                                LOGGER.error("Error while producing", exception);
                            }
                        }
                    });
                });
            }
            //executorService.shutdown();
        } catch (Exception e) {
            LOGGER.error("Error while producing", e);
        } finally {
            LOGGER.info("Producer is closed.");
            LocalDateTime endTime = LocalDateTime.now();
            LOGGER.info("Total time taken: " + (endTime.getSecond() - startTime.getSecond()) + " seconds");
        }
    }
}


