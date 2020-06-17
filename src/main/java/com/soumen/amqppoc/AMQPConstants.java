package com.soumen.amqppoc;

/**
 * @author Soumen Karmakar
 * 08/06/2020
 */
public interface AMQPConstants {

    String RESPONSE_QUEUE = "responseQueue";
    String RESPONSE_EXCHANGE = "responseExchange";
    String DELAY_QUEUE = RESPONSE_QUEUE + ".delay";
    String RESPONSE_ROUTING_KEY = "response.routing.key";
}
