package com.soumen.amqppoc;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Soumen Karmakar
 * 05/06/2020
 */
@Service
@Log4j2
public class CallBackDelayQueue implements CommandLineRunner {

    private static DelayQueue<DelayedDMMCall> delayQueue = new DelayQueue<>();
    private AtomicInteger counter;

    public static void enQueue(Message message, int delayAmount) {
        long trigger_ts = System.currentTimeMillis() + delayAmount;
        LocalDateTime triggerTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(trigger_ts),
                        TimeZone.getDefault().toZoneId());
        DelayedDMMCall delayedDMMCall = new DelayedDMMCall(message, triggerTime);
        delayQueue.add(delayedDMMCall);
        log.info("Added to delayQueue : "+delayedDMMCall);
    }


    public void processDMMCallback(Message message) {
        log.info("Processing DMM Callback for -- " + message);
    }

    @Override
    public void run(String... args) throws Exception {
        delayQueue.stream().forEach(delayedDMMCall -> {
            processDMMCallback(delayedDMMCall.getMessage());
        });
    }
}
