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
    DirectExchange exchange() {
        return new DirectExchange(RESPONSE_EXCHANGE);
    }

    @Bean
    Queue responseQueue() {
        return QueueBuilder.durable(RESPONSE_QUEUE)
                .deadLetterExchange(RESPONSE_EXCHANGE)
                .deadLetterRoutingKey(DELAY_QUEUE)
                .build();
    }

    @Bean
    Queue delayQueue() {
        return new Queue(DELAY_QUEUE);
    }

    @Bean
    Binding primaryBinding(Queue responseQueue, DirectExchange exchange) {
        return BindingBuilder.bind(responseQueue).to(exchange).with(RESPONSE_ROUTING_KEY);
    }

    @Bean
    Binding parkingBinding(Queue delayQueue, DirectExchange exchange) {
        return BindingBuilder.bind(delayQueue).to(exchange).with(DELAY_QUEUE);
    }
}
