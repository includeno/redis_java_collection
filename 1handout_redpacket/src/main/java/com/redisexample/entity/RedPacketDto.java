package com.redisexample.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author licha
 * @since 2020/7/29 10:28
 */
@Data
@ToString
public class RedPacketDto {

    private Integer userId;
    private Integer total;
    private Integer amount;
}
