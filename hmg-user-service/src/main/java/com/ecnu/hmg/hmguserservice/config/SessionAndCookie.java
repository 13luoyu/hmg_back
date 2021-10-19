package com.ecnu.hmg.hmguserservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class SessionAndCookie {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    /**
     * Because of the configuration, session will be saved in redis
     * @param username
     * @return
     */
    public String createNewSession(String username){
        HttpSession session=request.getSession();
        session.setAttribute("username", username);
        session.setMaxInactiveInterval(30*60);//30 min
        return session.getId();
    }

    public void createNewCookie(String sessionId){
        Cookie cookie=new Cookie("Hui_Min_Gou_Session_Id", sessionId);
        //不设置maxage，浏览会话结束时cookie到期
        //cookie.setMaxAge(12*60);//12 min
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void invalidSession(){
        HttpSession session=request.getSession();
        session.invalidate();
        Cookie[] cookies=request.getCookies();
    }

    public String getSessionId(){
        Cookie[] cookies=request.getCookies();
        if(cookies==null)
            return null;
        for(Cookie c:cookies){
            if(c.getName().equals("Hui_Min_Gou_Session_Id")){
                return c.getValue();
            }
        }
        return null;
    }

    public String getUsernameBySessionId(String sessionId){
        String sessionKey="spring:session:sessions:"+sessionId;
        try {
            String username = redisTemplate.opsForHash().get(sessionKey, "sessionAttr:username").toString();
            return username;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
