package com.redisexample.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author licha
 * @since 2020/7/29 11:06
 */
@Data
@Entity
@Table(name = "reddetail")
public class RedDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private Integer recordId;
    private BigDecimal amount;
    private Date createTime;
}
