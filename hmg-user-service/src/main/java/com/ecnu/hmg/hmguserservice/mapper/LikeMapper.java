package com.ecnu.hmg.hmguserservice.mapper;

import com.ecnu.hmg.hmguserservice.entity.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface LikeMapper extends PagingAndSortingRepository<UserLike, Integer>,
        JpaSpecificationExecutor<UserLike> {

    List<UserLike> getLikesByUsernameOrderByIdDesc(String username);

}
