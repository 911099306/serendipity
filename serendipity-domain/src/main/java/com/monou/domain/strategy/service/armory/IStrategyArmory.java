package com.monou.domain.strategy.service.armory;

import org.springframework.stereotype.Repository;

/**
 * @author serendipity
 * @description 策略装配库（兵工厂），负责初始化策略计算
 * @version 1.0
 * @date 2024/6/3
 **/
public interface IStrategyArmory {

    boolean assembleLotteryStrategy(Long strategyId);

    Integer getRandomAwardId(Long strategyId);
}
