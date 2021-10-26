package com.ecnu.hmg.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private int code;//0是user，1是shop
    private String content;
    private String option;//insert or delete or update
}
