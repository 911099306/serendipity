package com.monou.domain.strategy.service;

import com.monou.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;

/**
 * @author Serendipity
 * @description 策略奖品接口
 * @date 2024-07-03 00:03
 **/
public interface IRaffleAward {

    /**
     * 根据策略ID查询抽奖奖品列表配置
     *
     * @param strategyId 策略ID
     * @return 奖品列表
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);

    /**
     * 根据活动ID查询抽奖奖品列表配置
     *
     * @param activityId 活动ID
     * @return 奖品列表
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardListByActivityId(Long activityId);


}
