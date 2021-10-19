package com.ecnu.hmg.hmggoodshopservice.mqtest;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String content){
        rabbitTemplate.convertAndSend("topic-exchange","mq.key",content);
    }

}
