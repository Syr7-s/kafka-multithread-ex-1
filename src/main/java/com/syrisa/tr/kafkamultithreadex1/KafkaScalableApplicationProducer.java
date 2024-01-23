package com.syrisa.tr.kafkamultithreadex1;

import com.syrisa.tr.kafkamultithreadex1.scalable.producer.KafkaScalableStudentProducer;

public class KafkaScalableApplicationProducer {
    public static void main(String[] args) {
        KafkaScalableStudentProducer.runScalableProducer();
    }
}
