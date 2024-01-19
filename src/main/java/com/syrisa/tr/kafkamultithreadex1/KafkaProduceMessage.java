package com.syrisa.tr.kafkamultithreadex1;

import com.syrisa.tr.kafkamultithreadex1.common.KafkaTopic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KafkaProduceMessage {

    private static Map<String, List<String>> genericMessage = new HashMap<>();

    static {
        genericMessage.put(KafkaTopic.STUDENT_TOPIC.getTopicName(), new ArrayList<>());
        genericMessage.put(KafkaTopic.LEARNING_TOPIC.getTopicName(), new ArrayList<>());
        genericMessage.put(KafkaTopic.TEACHER_TOPIC.getTopicName(), new ArrayList<>());
        genericMessage.put(KafkaTopic.SCHOOL_TOPIC.getTopicName(), new ArrayList<>());
        genericMessage.put(KafkaTopic.CLASSROOM_TOPIC.getTopicName(), new ArrayList<>());

    }

    public static Map<String, List<String>> getGenericMessage() {
        return genericMessage;
    }

}
