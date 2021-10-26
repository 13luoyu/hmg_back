package com.ecnu.hmg.hmguserservice.controller;


import com.alibaba.fastjson.JSON;
import com.ecnu.hmg.hmguserservice.entity.UserLike;
import com.ecnu.hmg.hmguserservice.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class LikeController {
    @Autowired
    LikeService likeService;

    @RequestMapping("/add/like")
    public String addLike(@RequestParam(required = false) String username, Integer goodid){
        return likeService.addNewLike(username, goodid);
    }

    @RequestMapping("/delete/like")
    public String deleteLike(Integer id){
        return likeService.deleteById(id);
    }

    @RequestMapping("/get/like")
    public List<UserLike> getLike(@RequestParam(required = false) Integer pageNum){
        if(pageNum==null)
            pageNum=0;
        return likeService.getLikes(pageNum);
    }

    @RequestMapping("/search/like")
    public List<UserLike> searchLike(@RequestParam String username, @RequestParam(required = false) Integer pageNum){
        if(pageNum==null)
            pageNum=0;
        return likeService.getLikesByUsername(username, pageNum);
    }
}
