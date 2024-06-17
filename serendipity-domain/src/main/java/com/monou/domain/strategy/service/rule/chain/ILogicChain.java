package com.monou.domain.strategy.service.rule.chain;

/**
 * @author Serendipity
 * @description 抽奖策略规则责任链接口
 * @date 2024-06-17 22:11
 **/
public interface ILogicChain extends ILogicChainArmory {


    /**
     * 责任链接口
     *
     * @param userId     用户id
     * @param strategyId 策略id
     * @return 奖品id
     */
    Integer logic(String userId, Long strategyId);

}
