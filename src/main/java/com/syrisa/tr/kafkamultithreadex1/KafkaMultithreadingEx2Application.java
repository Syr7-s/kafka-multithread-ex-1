package com.syrisa.tr.kafkamultithreadex1;

import com.syrisa.tr.kafkamultithreadex1.consumer.KafkaStudentConsumer;
import com.syrisa.tr.kafkamultithreadex1.producer.KafkaMultithreadingStudentProducer;

import java.util.logging.Logger;

public class KafkaMultithreadingEx2Application {
    private static final Logger LOGGER = Logger.getLogger(KafkaMultithreadingEx2Application.class.getName());
    public static void main(String[] args) {
        try{
            KafkaStudentConsumer.runConsumer();
        }
        catch (Exception e){
           LOGGER.info("Please enter a valid object type.");
        }
    }
}
