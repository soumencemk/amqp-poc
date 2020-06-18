package com.soumen.amqppoc;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.soumen.amqppoc.AMQPConstants.DELAY_QUEUE;
import static com.soumen.amqppoc.AMQPConstants.RESPONSE_EXCHANGE;

/**
 * @author Soumen Karmakar
 * 20/05/2020
 */
@Component
@Log4j2
public class Publisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(String msg, int delay) {
        Message message = formMessage(msg, delay);
        log.info("Publishing : " + msg);
        rabbitTemplate.convertAndSend(RESPONSE_EXCHANGE, DELAY_QUEUE, message);
    }

    private Message formMessage(String msg) {
        return formMessage(msg, 0);
    }

    private Message formMessage(String msg, int delay) {
        msg = msg + " :: " + new Date().toString();
        SimpleMessageConverter smc = new SimpleMessageConverter();
        MessageProperties msgProp = new MessageProperties();
        msgProp.setExpiration(String.valueOf(delay));
        Message message = smc.toMessage(msg, msgProp);
        return message;
    }
}
