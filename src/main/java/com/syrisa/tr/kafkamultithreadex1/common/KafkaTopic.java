package com.syrisa.tr.kafkamultithreadex1.common;

public enum KafkaTopic {
    STUDENT_TOPIC("kafka-student-topic"),
    LEARNING_TOPIC("kafka-learning-topic"),
    TEACHER_TOPIC("kafka-teacher-topic"),
    SCHOOL_TOPIC("kafka-school-topic"),
    CLASSROOM_TOPIC("kafka-classroom-topic"),
    ;

    private String topicName;

    KafkaTopic(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}
