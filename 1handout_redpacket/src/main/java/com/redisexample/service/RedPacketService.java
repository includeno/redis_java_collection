package com.redisexample.service;


import com.redisexample.entity.RedPacketDto;
import com.redisexample.util.RedPacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import cn.hutool.core.lang.Snowflake;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
/**
 * @author licha
 * @since 2020/7/29 10:25
 */
@Service
public class RedPacketService implements IRedPacketService {

    private static final Logger log = LoggerFactory.getLogger(RedPacketService.class);

    private final Snowflake snowFlake=new Snowflake(2, 3);

    private static final String keyPrefix = "redis:red:packet:";


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private IRedPacketService redPacketService;

    @Autowired
    private IRedService redService;

    /**
     * 发红包
     *
     * @throws Exception
     */
    @Override
    public String handOut(RedPacketDto dto) throws Exception {
        if (dto.getTotal() > 0 && dto.getAmount() > 0) {
            //生成随机金额
            List<Integer> list = RedPacketUtil.divideRedPackagev2(dto.getAmount(), dto.getTotal(),true);

            //生成红包全局唯一标识,并将随机金额、个数入缓存
            String timeStamp = String.valueOf(System.nanoTime());
            String redisId = new StringBuffer(keyPrefix).append(dto.getUserId()).append(":").append(timeStamp).toString();
            redisTemplate.opsForList().leftPushAll(redisId, list);

            String redisTotal = redisId + ":total";
            redisTemplate.opsForValue().set(redisTotal, dto.getTotal());
            //异步记录红包发出的记录-包括个数与随机金额
            redService.recordRedPacket(dto, redisId, list);
            return redisId;
        } else {
            throw new Exception("系统异常-分发红包-参数不合法!");
        }
    }


    /**
     * 不加分布式锁的情况
     * 抢红包-分“点”与“抢”处理逻辑
     * @param userId
     * @param redId
     * @return
     * @throws Exception
     */
    @Override
    public BigDecimal rob(Integer userId,String redId) throws Exception {
        ValueOperations valueOperations=redisTemplate.opsForValue();

        //用户是否抢过该红包
        Object obj=valueOperations.get(redId+userId+":rob");
        if (obj!=null){
            return new BigDecimal(obj.toString());
        }

        //"点红包"
        Boolean res=click(redId);
        if (res){
            //"抢红包"-且红包有钱
            Object value=redisTemplate.opsForList().rightPop(redId);
            if (value!=null){
                //红包个数减一
                String redTotalKey = redId+":total";

                Integer currTotal=valueOperations.get(redTotalKey)!=null? (Integer) valueOperations.get(redTotalKey) : 0;
                valueOperations.set(redTotalKey,currTotal-1);


                //将红包金额返回给用户的同时，将抢红包记录入数据库与缓存
                BigDecimal result = new BigDecimal(value.toString()).divide(new BigDecimal(100));
                redService.recordRobRedPacket(userId,redId,new BigDecimal(value.toString()));

                valueOperations.set(redId+userId+":rob",result,24L,TimeUnit.HOURS);

                log.info("当前用户抢到红包了：userId={} key={} 金额={} ",userId,redId,result);
                return result;
            }

        }
        return null;
    }


    /**
     * 点红包-返回true，则代表红包还有，个数>0
     *
     * @throws Exception
     */
    private Boolean click(String redId) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object total = valueOperations.get(redId + ":total");
        if (total != null && (Integer.valueOf(total.toString()) > 0)) {
            return true;
        }
        return false;
    }
}
