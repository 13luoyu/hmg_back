package com.ecnu.hmg.hmggoodshopservice.mq;

import com.alibaba.fastjson.JSON;
import com.ecnu.hmg.hmggoodshopservice.controller.GoodController;
import com.ecnu.hmg.hmggoodshopservice.controller.ShopController;
import com.ecnu.hmg.hmggoodshopservice.entity.Good;
import com.ecnu.hmg.hmggoodshopservice.entity.Good_Shop;
import com.ecnu.hmg.hmggoodshopservice.entity.MyMessage;
import com.ecnu.hmg.hmggoodshopservice.entity.Shop;
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
    private GoodController goodController;
    @Autowired
    private ShopController shopController;

    @RabbitListener(queues = "queue2")
    public void processMessage(Message message){
        byte [] b=message.getBody();
        try {
            MyMessage myMessage=JSON.parseObject(new String(b), MyMessage.class);
            switch (myMessage.getOption().toLowerCase()) {
                case "addgood":
                    Map map=JSON.parseObject(myMessage.getContent(), Map.class);
                    Good_Shop good_shop=new Good_Shop();
                    good_shop.setShopId((Integer)map.get("shopid"));
                    good_shop.setDescription(map.get("description").toString());
                    good_shop.setName(map.get("name").toString());
                    good_shop.setPrice((Double) map.get("price"));
                    good_shop.setType(map.get("type").toString());
                    goodController.addGood(good_shop);
                    break;
                case "updategood":
                    map=JSON.parseObject(myMessage.getContent(), Map.class);
                    Good good=new Good();
                    good.setId((Integer) map.get("id"));
                    good.setPrice("￥"+map.get("price").toString());
                    good.setType(map.get("type").toString());
                    good.setDescription(map.get("description").toString());
                    good.setName(map.get("name").toString());
                    goodController.updateGood(good);
                    break;
                case "deletegood":
                    goodController.deleteGood(Integer.parseInt(JSON.parseObject(myMessage.getContent(), Map.class).get("id").toString()));
                    break;
                case "addshop":
                    map=JSON.parseObject(myMessage.getContent(), Map.class);
                    System.out.println(myMessage.getContent());
                    Shop shop=new Shop();
                    shop.setName(map.get("name").toString());
                    shop.setLocation(map.get("location").toString());
                    shop.setDescription(map.get("description").toString());
                    shopController.addShop(shop);
                    break;
                case "updateshop":
                    map=JSON.parseObject(myMessage.getContent(), Map.class);
                    System.out.println(myMessage.getContent());
                    shop=new Shop();
                    shop.setName(map.get("name").toString());
                    shop.setLocation(map.get("location").toString());
                    shop.setDescription(map.get("description").toString());
                    shopController.updateShop( JSON.parseObject(myMessage.getContent(), Shop.class));
                    break;
                case "deleteshop":
                    shopController.deleteShop(Integer.parseInt(JSON.parseObject(myMessage.getContent(), Map.class).get("id").toString()));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
