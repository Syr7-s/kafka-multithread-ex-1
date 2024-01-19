package com.syrisa.tr.kafkamultithreadex1;


import com.syrisa.tr.kafkamultithreadex1.producer.KafkaMultithreadingStudentProducer;

public class KafkaMultithreadEx1Application {

    public static void main(String[] args) {
        try {
            KafkaMultithreadingStudentProducer.runProducer();

        } catch (Exception e) {
           // System.out.println("Please enter a valid object type.");
        }
    }

}
