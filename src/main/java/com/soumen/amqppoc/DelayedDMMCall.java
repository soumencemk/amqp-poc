package com.soumen.amqppoc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.amqp.core.Message;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author Soumen Karmakar
 * 05/06/2020
 */
@Getter
@Setter
@ToString
public class DelayedDMMCall implements Delayed {
    private Message message;
    private LocalDateTime activationDateTime;

    public DelayedDMMCall(Message message, LocalDateTime activationDateTime) {
        super();
        this.message = message;
        this.activationDateTime = activationDateTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        LocalDateTime now = LocalDateTime.now();
        long diff = now.until(activationDateTime, ChronoUnit.MILLIS);
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return ((Long) getDelay(TimeUnit.MILLISECONDS)).compareTo(o.getDelay(TimeUnit.MILLISECONDS));
    }
}
