package com.syrisa.tr.kafkamultithreadex1.scalable.producer;

import com.syrisa.tr.kafkamultithreadex1.common.CustomThreadFactory;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class AutoProductGenerateHouse {
    private static final Random RANDOM = new Random();
    private static final ThreadFactory threadFactory = new CustomThreadFactory("KafkaScalableProducer");
    private static final ExecutorService executorService = Executors.newFixedThreadPool(5, threadFactory);
    private static final List<ProducerRecord<String, String>> productsData = new ArrayList<>();

    private AutoProductGenerateHouse() {
    }

    public static List<ProducerRecord<String, String>> getProducerRecord() {
        try (executorService) {
            int startKey = RANDOM.nextInt(50000);
            TimeUnit.SECONDS.sleep(5);
            for (int i = 1; i < startKey + 10; i++) {
                executorService.execute(
                        new KafkaProduceData(
                                new ProducerRecord<>(
                                        "kafka.scalable.products",
                                        String.valueOf(RANDOM.nextInt(1000, 9999)) + " numbered customer : ",
                                        Product.getProduct(RANDOM.nextInt(1, Product.getProductListSize())) + " named product bought"
                                )));
            }

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
        return productsData;

    }

    static class KafkaProduceData implements Runnable {

        private final ProducerRecord<String, String> producerRecord;

        public KafkaProduceData(ProducerRecord<String, String> producerRecord) {
            this.producerRecord = producerRecord;
        }

        @Override
        public void run() {
            productsData.add(producerRecord);
        }
    }
}
