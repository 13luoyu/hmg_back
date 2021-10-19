package com.ecnu.hmg.hmggoodshopservice;

import com.ecnu.hmg.hmggoodshopservice.mqtest.Consumer;
import com.ecnu.hmg.hmggoodshopservice.mqtest.Producer;
import com.ecnu.hmg.hmggoodshopservice.service.Recommend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HmgGoodShopServiceApplicationTests {

    @Autowired
    Recommend recommend;

    @Test
    void contextLoads() {
    }

    @Autowired
    private Producer producer;

    //Direct
    @Test
    public void sendDirectMsg() {
        producer.sendMsg("hi");
    }

}
