package com.ecnu.hmg.mq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Producer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendmsg(Message message){
        //解析url中的参数
        String content=message.getContent();
        Map<String, Object> map=new HashMap<>();
        StringBuilder key=new StringBuilder();
        StringBuilder value=new StringBuilder();
        int kv=0;
        for(int i=0;i<content.length();i++){
            if(content.charAt(i)=='='){
                kv=1;
            }else if(content.charAt(i)==','){
                kv=0;
                String k=new String(key);
                String v=new String(value);
                key=new StringBuilder();
                value=new StringBuilder();
                map.put(k,v);
            }else{
                if(kv==0)
                    key.append(content.charAt(i));
                else
                    value.append(content.charAt(i));
            }
        }
        String k=new String(key);
        String v=new String(value);
        map.put(k,v);
        message.setContent(JSON.toJSONString(map));


        if(message.getCode()==0)
            rabbitTemplate.convertAndSend("direct-exchange", "user", JSON.toJSONString(message));
        else if(message.getCode()==1)
            rabbitTemplate.convertAndSend("direct-exchange", "goodshop", JSON.toJSONString(message));
    }
}
