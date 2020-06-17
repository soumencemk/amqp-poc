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
    private final DMMCallbackProcessor dmmCallbackProcessor;

    @RabbitListener(queues = "RESPONSE_QUEUE")
    public void receiveMsg(Message message) throws Exception {
        long timeToExecute = (long) message.getMessageProperties().getHeader("timeToExecute");
        if (System.currentTimeMillis() < timeToExecute) {
            throw new Exception("Not your time, off you go..");
        } else {
            dmmCallbackProcessor.processDMMCallback(message);
        }
    }
}
