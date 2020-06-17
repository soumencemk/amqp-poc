package com.soumen.amqppoc;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveMsg(String message) {
        log.info("Processing DMM Callback for -- " + message);
    }
}
