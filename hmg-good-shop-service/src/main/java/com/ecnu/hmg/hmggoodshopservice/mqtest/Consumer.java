package com.ecnu.hmg.hmggoodshopservice.mqtest;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitListener(queues = "queue")
    public void processMessage(Message message){
        System.out.println(new String(message.getBody()));
    }

}
