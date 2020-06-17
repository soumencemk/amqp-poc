package com.soumen.amqppoc;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Soumen Karmakar
 * 20/05/2020
 */
@Component
@Log4j2
public class Publisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingkey}")
    private String routingKey;
    private static Random r = new Random();

    public void publish(String msg) {
        DelayCallbacks.registerDelayedCallback((long) r.nextInt(2000), messageObj -> {
            log.info("Publishing after delay");
            rabbitTemplate.convertAndSend(exchange, routingKey, messageObj);
        }, msg);
    }
}
