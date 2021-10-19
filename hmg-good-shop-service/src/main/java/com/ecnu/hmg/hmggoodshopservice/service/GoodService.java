package com.ecnu.hmg.hmggoodshopservice.service;

import com.alibaba.fastjson.JSON;
import com.ecnu.hmg.hmggoodshopservice.entity.G_S;
import com.ecnu.hmg.hmggoodshopservice.entity.Good;
import com.ecnu.hmg.hmggoodshopservice.entity.Good_Shop;
import com.ecnu.hmg.hmggoodshopservice.mapper.G_SMapper;
import com.ecnu.hmg.hmggoodshopservice.mapper.GoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ListFactoryBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class GoodService {
    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    private G_SMapper g_sMapper;

    public List<Good> getGoods(int pageNum){
        Pageable pageable=PageRequest.of(pageNum, 9);
        return goodMapper.findAll(pageable).getContent();
    }

    public Good getGoodById(int id){
        return goodMapper.findGoodById(id);
    }

    public String addNewGood(Good_Shop good_shop){
        try{
            Good g=new Good();
            g.setDescription(good_shop.getDescription());
            g.setName(good_shop.getName());
            g.setPrice("￥"+good_shop.getPrice());
            g.setType(good_shop.getType());
            Good good=goodMapper.save(g);

            G_S g_s=new G_S(good.getId(), good_shop.getShopId());
            g_sMapper.save(g_s);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "add success";
    }

    public List<Good> getGoodsByNameAndDescription(String name, Integer pageNum){
        if(pageNum==null)
            pageNum=0;
        Specification<Good> specification=new Specification<Good>() {
            @Override
            public Predicate toPredicate(Root<Good> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                predicates.add(criteriaBuilder.like(
                        root.get("name"),"%"+name+"%"));
                predicates.add(criteriaBuilder.like(
                        root.get("description"), "%"+name+"%"));
                return criteriaBuilder.or(predicates.toArray(
                        new Predicate[predicates.size()]));
            }
        };
        Pageable pageable= PageRequest.of(pageNum, 9);
        Page<Good> pg=goodMapper.findAll(specification, pageable);
        return pg.getContent();
    }

    public List<Good> getGoodsByType(String type, Integer pageNum){
        if(pageNum==null)
            pageNum=0;
        Specification<Good> specification=new Specification<Good>() {
            @Override
            public Predicate toPredicate(Root<Good> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
                return criteriaBuilder.and(predicates.toArray(
                        new Predicate[predicates.size()]));
            }
        };
        Pageable pageable=PageRequest.of(pageNum, 9);
        Page<Good> pg=goodMapper.findAll(specification, pageable);
        return pg.getContent();
    }

    public List<Good> getGoodsByShop(Integer shopId, Integer pageNum){
        if(pageNum==null)
            pageNum=0;
        List<G_S> g_sList=g_sMapper.findAllByShopId(shopId);
        List<Good> goodList=new ArrayList<>();
        for(G_S g_s:g_sList){
            Integer goodId=g_s.getGoodId();
            Good good=goodMapper.findById(goodId).get();
            goodList.add(good);
        }
        return goodList;
    }

    public String deleteGood(Integer goodId){
        try{
            goodMapper.deleteById(goodId);
            g_sMapper.deleteById(goodId);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "delete success";
    }

    public String updateGood(Good good){
        Good newGood=goodMapper.findById(good.getId()).get();
        if(good.getType()!=null){
            newGood.setType(good.getType());
        }
        if(good.getPrice()!=null){
            newGood.setPrice(good.getPrice());
        }
        if(good.getName()!=null){
            newGood.setName(good.getName());
        }
        if(good.getDescription()!=null){
            newGood.setDescription(good.getDescription());
        }
        try{
            goodMapper.save(newGood);
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
        return "update success";
    }


}
