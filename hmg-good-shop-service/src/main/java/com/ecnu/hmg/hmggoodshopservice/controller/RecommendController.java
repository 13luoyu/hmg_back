package com.ecnu.hmg.hmggoodshopservice.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.hmg.hmggoodshopservice.entity.Good;
import com.ecnu.hmg.hmggoodshopservice.service.Recommend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecommendController {
    @Autowired
    private Recommend recommend;

    @RequestMapping("/recommend")
    public List<Good> Recommend(@RequestParam(required = false) Integer page,
                                @RequestParam(required = false) String username){
        if(page==null)  page=0;
        return recommend.recommand(page, username);
    }

}
