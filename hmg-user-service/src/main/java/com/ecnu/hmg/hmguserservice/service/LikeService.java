package com.ecnu.hmg.hmguserservice.service;

import com.ecnu.hmg.hmguserservice.config.SessionAndCookie;
import com.ecnu.hmg.hmguserservice.entity.UserLike;
import com.ecnu.hmg.hmguserservice.mapper.LikeMapper;
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
public class LikeService {

    @Autowired
    LikeMapper likeMapper;
    @Autowired
    SessionAndCookie sessionAndCookie;

    public List<UserLike> getLikes(Integer pageNum){
        String username=sessionAndCookie.getUsernameBySessionId(
                sessionAndCookie.getSessionId());
        if(username==null)
            return null;
        //return likeMapper.getLikesByUsernameOrderByIdDesc(username);
        return likeMapper.findAll(new Specification<UserLike>() {
            @Override
            public Predicate toPredicate(Root<UserLike> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("username"), username));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, PageRequest.of(pageNum, 9, Sort.by("id").descending())).getContent();
    }

    public List<UserLike> getLikesByUsername(String username, Integer pageNum){
        //return likeMapper.getLikesByUsernameOrderByIdDesc(username);
        return likeMapper.findAll(new Specification<UserLike>() {
            @Override
            public Predicate toPredicate(Root<UserLike> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("username"), username));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, PageRequest.of(pageNum, 9, Sort.by("id").descending())).getContent();
    }

    public String addNewLike(String username, Integer goodid){
        UserLike userLike =new UserLike();
        userLike.setGoodid(goodid);
        if(username==null)
            username=sessionAndCookie.getUsernameBySessionId(
                sessionAndCookie.getSessionId());
        userLike.setUsername(username);

        try{
            likeMapper.save(userLike);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "Add success";
    }

    public String deleteById(Integer id){
        try{
            likeMapper.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "Delete success";
    }
}
