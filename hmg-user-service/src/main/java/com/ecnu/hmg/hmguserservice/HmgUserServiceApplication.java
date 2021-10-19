package com.ecnu.hmg.hmguserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
public class HmgUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmgUserServiceApplication.class, args);
    }

}
