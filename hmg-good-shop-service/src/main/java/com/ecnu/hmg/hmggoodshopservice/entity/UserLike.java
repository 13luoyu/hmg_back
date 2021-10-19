package com.ecnu.hmg.hmggoodshopservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLike {
    private Integer id;
    private String username;
    private Integer goodid;
}
