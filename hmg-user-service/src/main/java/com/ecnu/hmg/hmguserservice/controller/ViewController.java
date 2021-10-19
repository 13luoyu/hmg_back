package com.ecnu.hmg.hmguserservice.controller;

import com.alibaba.fastjson.JSON;
import com.ecnu.hmg.hmguserservice.entity.UserView;
import com.ecnu.hmg.hmguserservice.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ViewController {
    @Autowired
    ViewService viewService;

    @RequestMapping("/add/view")
    public String addView(Integer goodid){
        return viewService.addNewView(goodid);
    }

    @RequestMapping("/get/view")
    public List<UserView> getView(@RequestParam(required = false) Integer pageNum){
        if(pageNum==null)
            pageNum=0;
        return viewService.getLikes(pageNum);
    }

    @RequestMapping("/search/view")
    public List<UserView> searchView(@RequestParam String username,
                                     @RequestParam(required = false) Integer pageNum){
        if(pageNum==null)
            pageNum=0;
        return viewService.getLikesByUsername(username, pageNum);
    }

    @RequestMapping("/delete/view")
    public String deleteView(Integer id){
        return viewService.deleteById(id);
    }

}
