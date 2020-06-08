package com.soumen.amqppoc;

/**
 * @author Soumen Karmakar
 * 08/06/2020
 */
public enum AMQPConstants {

    RESPONSE("RESPONSE_EXCHANGE", "resonse.routing.key"),
    DLX("DLX_EXCHANGE", "RESPONSE_EXCHANGE");

    private String exchange;
    private String routingKey;

    AMQPConstants(String exchange, String routingKey) {
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public String getExchange() {
        return exchange;

    }
}
