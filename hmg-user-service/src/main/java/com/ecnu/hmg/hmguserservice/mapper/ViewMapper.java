package com.ecnu.hmg.hmguserservice.mapper;

import com.ecnu.hmg.hmguserservice.entity.UserView;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ViewMapper extends PagingAndSortingRepository<UserView, Integer>,
        JpaSpecificationExecutor<UserView> {

    List<UserView> getUserViewsByUsernameOrderByIdDesc(String username);

}
