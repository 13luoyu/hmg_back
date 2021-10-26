package com.ecnu.hmg.hmguserservice.controller;

import com.ecnu.hmg.hmguserservice.entity.User;
import com.ecnu.hmg.hmguserservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login(User user){
        return userService.login(user);
    }

    @RequestMapping("/register")
    public String register(User user){
        return userService.addUser(user);
    }

    @RequestMapping("/logout")
    public String logout(){
        return userService.logout();
    }
}
