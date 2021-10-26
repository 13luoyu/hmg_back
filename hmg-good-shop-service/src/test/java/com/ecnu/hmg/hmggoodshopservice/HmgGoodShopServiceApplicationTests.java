package com.ecnu.hmg.hmggoodshopservice;

import com.ecnu.hmg.hmggoodshopservice.service.Recommend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HmgGoodShopServiceApplicationTests {

    @Autowired
    Recommend recommend;

    @Test
    void contextLoads() {
    }

}
