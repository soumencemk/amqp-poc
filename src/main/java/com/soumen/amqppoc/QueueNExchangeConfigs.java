package com.soumen.amqppoc;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.soumen.amqppoc.AMQPConstants.*;

/**
 * @author Soumen Karmakar
 * 17/06/2020
 */
@Configuration
public class QueueNExchangeConfigs {

    @Bean
    DirectExchange responseExchange() {
        return new DirectExchange(RESPONSE_EXCHANGE);
    }

    @Bean
    Queue responseQueue() {
        return new Queue(RESPONSE_QUEUE);
    }

    @Bean
    Queue delayQueue() {
        return QueueBuilder.durable(DELAY_QUEUE)
                .deadLetterExchange(RESPONSE_EXCHANGE)
                .deadLetterRoutingKey(RESPONSE_ROUTING_KEY)
                .build();
    }

    @Bean
    Binding primaryBinding(Queue responseQueue, DirectExchange responseExchange) {
        return BindingBuilder.bind(responseQueue).to(responseExchange).with(RESPONSE_ROUTING_KEY);
    }

    @Bean
    Binding parkingBinding(Queue delayQueue, DirectExchange responseExchange) {
        return BindingBuilder.bind(delayQueue).to(responseExchange).with(DELAY_QUEUE);
    }
}
