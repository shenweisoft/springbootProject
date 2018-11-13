package com.jy.config.rabbit;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "helloQueue")
public class RabbitReceiver {

    @RabbitHandler
    public void process(RabbitMessage rabbitMessage) {
        System.out.println("Receiver1  : " + rabbitMessage);
    }
}
