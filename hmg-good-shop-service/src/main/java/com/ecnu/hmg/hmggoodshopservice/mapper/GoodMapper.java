package com.ecnu.hmg.hmggoodshopservice.mapper;

import com.ecnu.hmg.hmggoodshopservice.entity.Good;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GoodMapper extends PagingAndSortingRepository<Good, Integer>,
        JpaSpecificationExecutor<Good> {
    public Good findGoodById(Integer id);
}
