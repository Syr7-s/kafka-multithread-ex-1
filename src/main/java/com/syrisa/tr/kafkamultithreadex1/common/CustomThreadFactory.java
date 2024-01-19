package com.syrisa.tr.kafkamultithreadex1.common;

import java.util.concurrent.ThreadFactory;

public class CustomThreadFactory implements ThreadFactory {

    private String threadName;
    private int counter = 1;
    public CustomThreadFactory(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadName + "-" + counter++);
        return thread;
    }
}
