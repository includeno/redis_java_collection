package com.redisexample.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author licha
 * @since 2020/7/29 10:22
 */
public class RedPacketUtil {
    /**
     * @param totalAmount    总金额 单位分
     * @param totalPeopleNum 总人数
     * @return
     */
    //输入总金额和总人数 返回随机金额列表
    public static List<Integer> divideRedPackage(Integer totalAmount, Integer totalPeopleNum) {
        List<Integer> resultList = new ArrayList<Integer>();
        Random random = new Random();
        Integer restTotalNum = totalPeopleNum;
        Integer restTotalAmount = totalAmount;
        for (int i = 0; i < totalPeopleNum; i++) {

            Integer mount = random.nextInt(restTotalAmount / restTotalNum * 2 - 1) + 1;
            restTotalAmount -= mount;
            restTotalNum--;
            resultList.add(mount);
        }
        resultList.add(restTotalAmount);
        return resultList;
    }

    /**
     * 二倍均值法生产红包
     *
     * @param totalAmount    总金额 单位分
     * @param totalPeopleNum 总人数
     * @param fullmoney 是否所有的钱都分完
     * @return
     */
    public static List<Integer> divideRedPackagev2(Integer totalAmount, Integer totalPeopleNum,Boolean fullmoney) {
        List<Integer> amountList = new ArrayList<>();
        if (totalAmount > 0 && totalPeopleNum > 0&&totalPeopleNum<=totalAmount) {
            Integer restAmount = totalAmount;
            Integer restPeopleNum = totalPeopleNum;
            Random random = new Random();
            for (int i = 0; i < totalPeopleNum; i++) {
                //保证红包里所有的钱发光
                if(i==totalPeopleNum-1&&fullmoney)
                {
                    amountList.add(restAmount);
                    break;
                }

                Integer temp = restAmount / restPeopleNum * 2 - 1;
                int amount = 0;
                if (temp > 0) {
                    amount=random.nextInt(temp) + 1;
                    restAmount -= amount;
                    restPeopleNum--;
                }
                amountList.add(amount);
            }
        }
        return amountList;

    }
}
