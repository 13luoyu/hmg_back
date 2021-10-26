package com.ecnu.hmg.hmguserservice.service;

import com.alibaba.fastjson.JSON;
import com.ecnu.hmg.hmguserservice.config.SessionAndCookie;
import com.ecnu.hmg.hmguserservice.entity.User;
import com.ecnu.hmg.hmguserservice.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


@Service
public class UserService {

    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected SessionAndCookie sc;

    public String addUser(User user){
        if(user.getUsername()==null || user.getPassword()==null || user.getEmail()==null)
            return "Username and Password and email are indispensable";
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        try{
            userMapper.save(user);
            return "Register success";
        }catch (Exception e){
            e.printStackTrace();
            return "Regsiter failed";
        }
    }

    public String login(User user){
        if(user.getUsername()==null || user.getPassword()==null)
            return "Username and Password are indispensable";
        try{
            User u =userMapper.findUserByUsername(user.getUsername());
            if(u==null){
                return "username not exist";
            }
            String password=DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            if(u.getPassword().equals(password)){
                String sessionId=sc.createNewSession(user.getUsername());
                sc.createNewCookie(sessionId);
                return "login success";
            }else{
                return "wrong password";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "Login error";
        }
    }

    public String logout(){
        String sessionId=sc.getSessionId();
        if(sessionId==null){
            return "No cookie&session found, please login first";
        }
        sc.invalidSession();
        return "Logout success";
    }
}
