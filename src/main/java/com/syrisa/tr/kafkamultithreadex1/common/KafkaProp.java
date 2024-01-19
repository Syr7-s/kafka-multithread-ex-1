package com.syrisa.tr.kafkamultithreadex1.common;

import com.syrisa.tr.kafkamultithreadex1.common.KafkaObject;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.RoundRobinAssignor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProp {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    public static Properties getProducerProp(KafkaObject kafkaObject){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        if (kafkaObject == KafkaObject.PRODUCER){
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        }else{
            properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
            properties.put(ConsumerConfig.GROUP_ID_CONFIG,"kafka-multithread-ex1");
            properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
         //   properties.put("partition.assignment.strategy", RoundRobinAssignor.class.getName());
        }
        return properties;
    }
}
