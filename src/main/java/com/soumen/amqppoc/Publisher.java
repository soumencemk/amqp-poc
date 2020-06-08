package com.soumen.amqppoc;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
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
    private static Random r = new Random();

    public void publish(String msg) {
        Message message = formMessage(msg);
        rabbitTemplate.convertAndSend(AMQPConstants.RESPONSE.getExchange(), AMQPConstants.RESPONSE.getRoutingKey(), message);
    }

    private Message formMessage(String msg) {
        msg = msg + " :: " + new Date().toString();
        SimpleMessageConverter smc = new SimpleMessageConverter();
        MessageProperties msgProp = new MessageProperties();
        Message message = smc.toMessage(msg, msgProp);
        return message;
    }
}
