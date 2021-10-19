package com.ecnu.hmg.hmggoodshopservice.mapper;

import com.ecnu.hmg.hmggoodshopservice.entity.Shop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopMapper extends CrudRepository<Shop, Integer> {

}
