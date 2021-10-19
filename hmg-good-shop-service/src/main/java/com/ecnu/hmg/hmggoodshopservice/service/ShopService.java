package com.ecnu.hmg.hmggoodshopservice.service;

import com.alibaba.fastjson.JSON;
import com.ecnu.hmg.hmggoodshopservice.entity.Shop;
import com.ecnu.hmg.hmggoodshopservice.mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ShopService {
    @Autowired
    private ShopMapper shopMapper;

    public List<Shop> getAllShops(){
        Iterator<Shop> it=shopMapper.findAll().iterator();
        List<Shop> shopList=new ArrayList<>();
        while(it.hasNext()){
            shopList.add(it.next());
        }
        return shopList;
    }

    public Shop getShopById(Integer id){
        return shopMapper.findById(id).get();
    }

    public String addShop(Shop shop){
        try{
            shopMapper.save(shop);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "add success";
    }

    public String updateShop(Shop shop){
        Shop newShop=shopMapper.findById(shop.getId()).get();
        if(shop.getName()!=null){
            newShop.setName(shop.getName());
        }
        if(shop.getLocation()!=null){
            newShop.setLocation(shop.getLocation());
        }
        if(shop.getDescription()!=null){
            newShop.setDescription(shop.getDescription());
        }
        try{
            shopMapper.save(newShop);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "update success";
    }

    public String delete(Integer shopId){
        try{
            shopMapper.deleteById(shopId);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "delete success";
    }

}
