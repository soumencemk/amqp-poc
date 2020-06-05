package com.soumen.amqppoc;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingkey}")
    private String routingKey;
    private static Random r = new Random();

    public void publish(String msg) {
        Message message = formMessage(msg);
        CorrelationData correlationData = new CorrelationData("Data : " + msg);
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);

    }

    private Message formMessage(String msg) {
        msg += new Date().toString();
        SimpleMessageConverter smc = new SimpleMessageConverter();
        MessageProperties msgProp = new MessageProperties();
        msgProp.setHeader("delayAmount", r.nextInt(2000));
        Message message = smc.toMessage(msg, msgProp);
        return message;
    }
}
