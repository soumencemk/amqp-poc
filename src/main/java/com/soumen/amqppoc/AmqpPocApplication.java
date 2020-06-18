package com.soumen.amqppoc;

import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Configuration
@EnableAsync
@RequiredArgsConstructor
@Log4j2
public class AmqpPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmqpPocApplication.class, args);
    }

    @Bean(name = "rabbitConnectionFactory")
    public CachingConnectionFactory connectionFactoryNoAck() {
        log.info("Creating connection factory for rabbit");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(factory);
        connectionFactory.setAddresses("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;

    }

    @Bean
    RabbitTemplate getRabbitTemplate(CachingConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}

@RestController
@RequiredArgsConstructor
class Controller {

    private final Publisher publisher;

    @GetMapping("/publish/{msg}/{delay}")
    public ResponseEntity<String> sendMessage(@PathVariable String msg, @PathVariable int delay) {
        publisher.publish(msg, delay);
        return new ResponseEntity(msg, HttpStatus.OK);
    }

}


