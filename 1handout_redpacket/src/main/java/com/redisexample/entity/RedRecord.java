package com.redisexample.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author licha
 * @since 2020/7/29 10:41
 */
@Data
@ToString
@Entity
@Table(name = "redrecord")
public class RedRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private Integer userId;
    private String redPacket;
    private Integer total;
    private BigDecimal amount;
    private Date createTime;

}
