package com.soumen.amqppoc;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

/**
 * @author Soumen Karmakar
 * 05/06/2020
 */
@Component
@Log4j2
public class DMMCallbackProcessor {
    public void processDMMCallback(Message message) {
        processDMMCallback(message, false);
    }
    public void processDMMCallback(Message message, boolean isDelay) {
        if (isDelay) {
            log.info("DMM Callback With delay -- " + message.getMessageProperties().getHeader("delayAmount"));

        } else {
            log.info("DMM Callback  -- " + message);
        }
    }
}
