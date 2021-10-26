package com.ecnu.hmg.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class Controller {
    @Autowired
    private Producer producer;

    @RequestMapping()
    public String getRequests(Message message){
        producer.sendmsg(message);
        return "success";
    }

}
