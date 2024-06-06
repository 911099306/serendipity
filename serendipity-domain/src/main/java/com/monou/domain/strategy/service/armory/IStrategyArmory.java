package com.monou.domain.strategy.service.armory;

import org.springframework.stereotype.Repository;

/**
 * @author serendipity
 * @description 策略装配库（兵工厂），负责初始化策略计算
 * @version 1.0
 * @date 2024/6/3
 **/
public interface IStrategyArmory {

    /**
     * 装配抽奖策略配置「触发的时机可以为活动审核通过后进行调用」
     *
     * @param strategyId 策略ID
     * @return 装配结果
     */
    boolean assembleLotteryStrategy(Long strategyId);


}
