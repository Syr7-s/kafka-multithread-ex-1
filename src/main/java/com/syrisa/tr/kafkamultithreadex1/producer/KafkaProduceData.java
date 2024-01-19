package com.syrisa.tr.kafkamultithreadex1.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.logging.Logger;

public class KafkaProduceData implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(KafkaProduceData.class.getSimpleName());
    private final KafkaProducer<String, String> simpleProducer;
    private final ProducerRecord<String, String> kafkaRecord;


    public KafkaProduceData(KafkaProducer<String, String> simpleProducer, ProducerRecord<String, String> kafkaRecord) {
        this.simpleProducer = simpleProducer;
        this.kafkaRecord = kafkaRecord;
    }

    @Override
    public void run() {

       // try {
           // System.out.println("Sending Message : " + kafkaRecord.toString() + " Thread name : " + Thread.currentThread().getName());
         //   simpleProducer.send(kafkaRecord);
            simpleProducer.send(kafkaRecord);
            System.out.println("Sending Message : " + kafkaRecord.toString() + " Thread name : " + Thread.currentThread().getName());

        try {
            Thread.sleep(500);
            //simpleProducer.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        } catch (InterruptedException e) {
//            // throw new RuntimeException(e);
//        } catch (Exception e) {
//            //throw new RuntimeException(e);
//        }
    }
}
