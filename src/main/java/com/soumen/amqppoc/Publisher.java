package com.soumen.amqppoc;

import com.soumen.Callback;
import com.soumen.DelayedCallbacks;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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
public class Publisher implements Callback {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingkey}")
    private String routingKey;
    private static Random r = new Random();

    public void publish(String msg) {
        Message message = formMessage(msg, r.nextInt(2000));
        Integer delay = message.getMessageProperties().getDelay();
        log.info("Delay for : " + delay);
        DelayedCallbacks.registerDelayedCallback(Long.valueOf(message.getMessageProperties().getDelay()), this, message);
    }

    private Message formMessage(String msg, int delay) {
        msg += " " + new Date().toString();
        SimpleMessageConverter smc = new SimpleMessageConverter();
        MessageProperties msgProp = new MessageProperties();
        msgProp.setDelay(delay);
        Message message = smc.toMessage(msg, msgProp);
        return message;
    }


    @Override
    public void performCallback(Object o) {
        if (o instanceof Message) {
            log.info("Publishing after delay");
            rabbitTemplate.convertAndSend(exchange, routingKey, o);
        }
    }

    public void publish(String msg, int delay) {
        Message message = formMessage(msg, delay);
        DelayedCallbacks.registerDelayedCallback(Long.valueOf(message.getMessageProperties().getDelay()), this, message);
    }
}
