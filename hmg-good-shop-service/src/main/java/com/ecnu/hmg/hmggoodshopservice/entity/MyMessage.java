package com.ecnu.hmg.hmggoodshopservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyMessage implements Serializable {
    private int code;//0是user，1是shop
    private String content;
    private String option;//insert or delete or update
}
