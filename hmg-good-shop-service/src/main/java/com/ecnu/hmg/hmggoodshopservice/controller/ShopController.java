package com.ecnu.hmg.hmggoodshopservice.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.hmg.hmggoodshopservice.entity.Good;
import com.ecnu.hmg.hmggoodshopservice.entity.Shop;
import com.ecnu.hmg.hmggoodshopservice.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class ShopController {
    @Autowired
    ShopService shopService;

    @RequestMapping("/search/shop")
    public Shop getShop(Integer id){
        return shopService.getShopById(id);
    }

    @RequestMapping("/shops")
    public List<Shop> getAllShops(){
        return shopService.getAllShops();
    }

    @RequestMapping("/add/shop")
    public String addShop(Shop shop){
        return shopService.addShop(shop);
    }

    @RequestMapping("/update/shop")
    public String updateShop(Shop shop){
        return shopService.updateShop(shop);
    }

    @RequestMapping("/delete/shop")
    public String deleteShop(Integer id){
        return shopService.delete(id);
    }
}
