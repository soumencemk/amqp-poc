package com.soumen.amqppoc;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Soumen Karmakar
 * 05/06/2020
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class Consumer {

    @RabbitListener(queues = "SV_QUEUE")
    public void receiveMsg(Message message) {
        int delayAmount = message.getMessageProperties().getHeader("delayAmount");
        if (delayAmount > 0) {
            CallBackDelayQueue.enQueue(message, delayAmount);
        } else {
            log.info("Processing DMM Callback without delay for -- " + message);
        }
    }
}