package com.ecnu.hmg.hmggoodshopservice.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.hmg.hmggoodshopservice.entity.Good;
import com.ecnu.hmg.hmggoodshopservice.entity.Good_Shop;
import com.ecnu.hmg.hmggoodshopservice.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
public class GoodController {
    @Autowired
    GoodService goodService;

    @RequestMapping("/search/good")
    public List<Good> searchGoods(String name, @RequestParam(required = false) Integer pageNum){
        if(pageNum==null)   pageNum=0;
        return goodService.getGoodsByNameAndDescription(name, pageNum);
    }

    @RequestMapping("/add/good")
    public String addGood(Good_Shop good_shop){
        return goodService.addNewGood(good_shop);
    }

    @RequestMapping("/search/good/type")
    public List<Good> getGoodsByType(String type, @RequestParam(required = false) Integer pageNum){
        if(pageNum==null)   pageNum=0;
        return goodService.getGoodsByType(type, pageNum);
    }

    @RequestMapping("/get/good")
    public Good getGoodById(Integer id){
        return goodService.getGoodById(id);
    }

    @RequestMapping("/search/good/shop")
    public List<Good> getGoodsByShop(Integer shopId, @RequestParam(required = false) Integer pageNum){
        if(pageNum==null)   pageNum=0;
        return goodService.getGoodsByShop(shopId, pageNum);
    }

    @RequestMapping("/delete/good")
    public String deleteGood(Integer id){
        return goodService.deleteGood(id);
    }

    @RequestMapping("/update/good")
    public String updateGood(Good good){
        return goodService.updateGood(good);
    }
}
