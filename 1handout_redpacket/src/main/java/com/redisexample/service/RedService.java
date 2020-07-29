package com.redisexample.service;

import com.redisexample.entity.RedDetail;
import com.redisexample.entity.RedPacketDto;
import com.redisexample.entity.RedRecord;
import com.redisexample.repository.RedDetailRepository;
import com.redisexample.repository.RedRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author licha
 * @since 2020/7/29 10:37
 */
@Slf4j
@Service
@EnableAsync
public class RedService implements IRedService {
    @Autowired
    RedRecordRepository redRecordRepository;

    @Autowired
    RedDetailRepository redDetailRepository;

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void recordRedPacket(RedPacketDto dto, String redId, List<Integer> list) throws Exception {
        RedRecord redRecord=new RedRecord();
        redRecord.setUserId(dto.getUserId());
        redRecord.setRedPacket(redId);
        redRecord.setTotal(dto.getTotal());
        redRecord.setAmount(BigDecimal.valueOf(dto.getAmount()));
        redRecord.setCreateTime(new Date());
        redRecordRepository.save(redRecord);

        RedDetail detail;
        for(Integer i:list)
        {
            detail=new RedDetail();
            detail.setRecordId(redRecord.getId());
            detail.setAmount(BigDecimal.valueOf(i));
            detail.setCreateTime(new Date());
            redDetailRepository.save(detail);
        }

    }

    @Override
    public void recordRobRedPacket(Integer userId, String redId, BigDecimal amount) throws Exception {

    }
}
