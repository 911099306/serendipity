package com.monou.domain.strategy.service.rule.chain;

import com.monou.domain.strategy.service.rule.chain.factory.DefaultChainFactory;

/**
 * @author Serendipity
 * @description 抽奖策略规则责任链接口
 * @date 2024-06-17 22:11
 **/
public interface ILogicChain extends ILogicChainArmory, Cloneable {


    /**
     * 责任链接口
     *
     * @param userId     用户id
     * @param strategyId 策略id
     * @return 抽奖结果
     */
    DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId);

}
