package com.ecnu.hmg.hmguserservice.mapper;

import com.ecnu.hmg.hmguserservice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends CrudRepository<User, String> {

    User findUserByUsername(String username);

}
