package com.soumen.amqppoc;

import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
@Configuration
@EnableAsync
@Log4j2
public class AmqpPocApplication implements CommandLineRunner {
    @Autowired
    private Publisher publisher;

    public static void main(String[] args) {
        SpringApplication.run(AmqpPocApplication.class, args);
    }


    @Override
    public void run(String... args) {
        Stream<String> stream = Stream.generate(() -> String.valueOf(new Random().nextInt(100000)));
        stream.forEach(s -> {
            publisher.publish(s);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Bean(name = "rabbitConnectionFactory")
    public CachingConnectionFactory connectionFactoryNoAck() {
        log.info("Creating connection factory for rabbit");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("hnsgcqkr");
        factory.setPassword("L11sg3m23njLwZxc33WZhHgdwL3L2He_");
        factory.setVirtualHost("hnsgcqkr");
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(factory);
        connectionFactory.setAddresses("bloodhound-01.rmq.cloudamqp.com");
        connectionFactory.setUsername("hnsgcqkr");
        connectionFactory.setPassword("L11sg3m23njLwZxc33WZhHgdwL3L2He_");
        return connectionFactory;

    }

    @Bean
    RabbitTemplate getRabbitTemplate(CachingConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}


