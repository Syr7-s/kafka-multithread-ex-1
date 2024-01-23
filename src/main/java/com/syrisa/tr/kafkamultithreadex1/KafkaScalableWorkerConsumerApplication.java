package com.syrisa.tr.kafkamultithreadex1;

import com.syrisa.tr.kafkamultithreadex1.scalable.consumer.KafkaScalableWorkerConsumer;

public class KafkaScalableWorkerConsumerApplication {
    public static void main(String[] args) {
        KafkaScalableWorkerConsumer.runWorkerConsumer();

    }
}
