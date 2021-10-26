package com.ecnu.hmg.hmggoodshopservice.mapper;

import com.ecnu.hmg.hmggoodshopservice.entity.G_S;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface G_SMapper extends JpaRepository<G_S, Integer> {

    List<G_S> findAllByShopId(Integer shopId);
    G_S findG_SByGoodId(Integer goodId);

}
