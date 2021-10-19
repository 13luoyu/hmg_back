package com.ecnu.hmg.hmggoodshopservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ecnu.hmg.hmggoodshopservice.entity.Good;
import com.ecnu.hmg.hmggoodshopservice.entity.UserLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class Recommend {
    @Autowired
    private GoodService goodService;
    @Value("${hmg.user.service.addr}")
    private String addr;

    public List<Good> recommand(int pageNum, String username){
        try {
            if(username==null) {//用户未登录
                List<Good> goods=goodService.getGoods(pageNum);
                return goods;
            }
            else{
                URL url = new URL("http://" + addr + "/search/like?username="+username+"&pageNum="+pageNum);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("GET");
                connection.connect();
                Scanner in = new Scanner(connection.getInputStream());
                String rs = in.next();
                //System.out.println(rs);
                connection.disconnect();

                List<UserLike> userLikes=JSON.parseArray(rs, UserLike.class);

                List<Good> goods=new ArrayList<>();
                for(UserLike userLike:userLikes){
                    int goodid=userLike.getGoodid();
                    Good good=goodService.getGoodById(goodid);
                    goods.add(good);
                }
                return goods;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
