package com.redisexample.service;

import com.redisexample.entity.RedPacketDto;

import java.math.BigDecimal;
import java.util.List;

public interface IRedService {

    void recordRedPacket(RedPacketDto dto, String redId, List<Integer> list)throws Exception;

    void recordRobRedPacket(Integer userId,String redId,BigDecimal amount)throws Exception;
}
