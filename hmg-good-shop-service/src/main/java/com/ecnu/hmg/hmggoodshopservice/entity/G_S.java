package com.ecnu.hmg.hmggoodshopservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "good_shop")
public class G_S {
    @Id
    @Column(name = "goodID")
    private Integer goodId;

    @Column(name = "shopID")
    private Integer shopId;
    
}
