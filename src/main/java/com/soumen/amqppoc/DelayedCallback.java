package com.soumen.amqppoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author Soumen Karmakar
 * 16/06/2020
 */

public class DelayedCallback {
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(50);

    DelayedCallback(Long delayTime, Consumer<String> callback, String message) {
        if (delayTime == 0l) {
            callback.accept(message);
        } else {
            executor.schedule(new PerformCallback(message, callback), delayTime, TimeUnit.MILLISECONDS);
        }
    }

    /**
     *
     */
    class PerformCallback implements Runnable {
        String message;
        private Consumer<String> callback;

        PerformCallback(String message, Consumer<String> callback) {
            this.message = message;
            this.callback = callback;
        }

        @Override
        public void run() {
            if (this.callback != null) {
                this.callback.accept(message);
            }
        }
    }
}
