package com.ecnu.hmg.hmguserservice.mq;

import com.alibaba.fastjson.JSON;
import com.ecnu.hmg.hmguserservice.controller.LikeController;
import com.ecnu.hmg.hmguserservice.controller.ViewController;
import com.ecnu.hmg.hmguserservice.entity.MyMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

@Component
public class Consumer {

    @Autowired
    private LikeController likeController;
    @Autowired
    private ViewController viewController;

    @RabbitListener(queues = "queue1")
    public void processMessage(Message message){
        byte [] b=message.getBody();
        try {
            MyMessage myMessage=JSON.parseObject(new String(b), MyMessage.class);
            switch (myMessage.getOption().toLowerCase()){
                case "addlike":
                    Map map=JSON.parseObject(myMessage.getContent(), Map.class);
                    String username=map.get("username").toString();
                    Integer goodid=Integer.parseInt(map.get("goodid").toString());
                    likeController.addLike(username, goodid);
                    break;
                case "deletelike":
                    likeController.deleteLike(Integer.parseInt (( JSON.parseObject(myMessage.getContent(), Map.class)).get("id").toString()));
                    break;
                case "addview":
                    map=JSON.parseObject(myMessage.getContent(), Map.class);
                    username=map.get("username").toString();
                    goodid=Integer.parseInt(map.get("goodid").toString());
                    viewController.addView(username, goodid);
                    break;
                case "deleteview":
                    viewController.deleteView(Integer.parseInt (( JSON.parseObject(myMessage.getContent(), Map.class)).get("id").toString()));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
