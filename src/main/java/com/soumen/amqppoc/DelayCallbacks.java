package com.soumen.amqppoc;

import java.util.function.Consumer;

/**
 * @author Soumen Karmakar
 * 17/06/2020
 */
public abstract class DelayCallbacks {
    public static void registerDelayedCallback(Long delayInMs, Consumer<String> callback, String message) {
        new DelayedCallback(delayInMs, callback, message);
    }
}
