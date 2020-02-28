package com.example.springall.messagingredis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class Receiver {
//    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
//
//    private AtomicInteger counter = new AtomicInteger();
//
//    public void recieveMessage(String message){
//        LOGGER.info("接收消息:<"+message+">");
//        counter.incrementAndGet();
//    }
//
//    public int getCount(){
//        return counter.get();
//    }
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private AtomicInteger counter = new AtomicInteger();

    public void receiveMessage(String message) {
        LOGGER.info("接收的消息： <" + message + ">");
        counter.incrementAndGet();
    }

    public int getCount() {
        return counter.get();
    }
}

