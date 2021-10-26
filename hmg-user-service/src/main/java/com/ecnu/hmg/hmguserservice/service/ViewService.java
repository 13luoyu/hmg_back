package com.ecnu.hmg.hmguserservice.service;

import com.ecnu.hmg.hmguserservice.config.SessionAndCookie;
import com.ecnu.hmg.hmguserservice.entity.UserLike;
import com.ecnu.hmg.hmguserservice.entity.UserView;
import com.ecnu.hmg.hmguserservice.mapper.ViewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class ViewService {
    @Autowired
    ViewMapper viewMapper;

    @Autowired
    SessionAndCookie sessionAndCookie;

    public List<UserView> getLikes(Integer pageNum){
        String username=sessionAndCookie.getUsernameBySessionId(
                sessionAndCookie.getSessionId());
        if(username==null)
            return null;
        //return viewMapper.getUserViewsByUsernameOrderByIdDesc(username);
        return viewMapper.findAll(new Specification<UserView>() {
            @Override
            public Predicate toPredicate(Root<UserView> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("username"), username));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, PageRequest.of(pageNum, 9, Sort.by("id").descending())).getContent();
    }

    public List<UserView> getLikesByUsername(String username, Integer pageNum){
        return viewMapper.findAll(new Specification<UserView>() {
            @Override
            public Predicate toPredicate(Root<UserView> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("username"), username));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, PageRequest.of(pageNum, 9, Sort.by("id").descending())).getContent();
    }

    public String addNewView(String username, Integer goodid){
        UserView userView=new UserView();
        userView.setGoodid(goodid);
        if(username==null)
            username=sessionAndCookie.getUsernameBySessionId(
                sessionAndCookie.getSessionId());
        userView.setUsername(username);

        try{
            viewMapper.save(userView);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "Add success";
    }

    public String deleteById(Integer id){
        try{
            viewMapper.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "Delete success";
    }
}
