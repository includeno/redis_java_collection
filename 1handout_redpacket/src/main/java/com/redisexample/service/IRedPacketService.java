package com.redisexample.service;

import com.redisexample.entity.RedPacketDto;

import java.math.BigDecimal;

/**
 * @author licha
 * @since 2020/7/29 10:25
 */
public interface IRedPacketService {

    public BigDecimal rob(Integer userId,String redId) throws Exception;

    public String handOut(RedPacketDto dto) throws Exception;

}
