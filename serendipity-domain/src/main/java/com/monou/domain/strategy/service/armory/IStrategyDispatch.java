package com.monou.domain.strategy.service.armory;

/**
 * @author serendipity
 * @description 策略抽奖调度
 * @version 1.0
 * @date 2024/6/6
 **/
public interface IStrategyDispatch {

    /**
     * 获取抽奖策略装配的随即结果
     *
     * @param strategyId 策略ID
     * @return 抽奖结果
     */
    Integer getRandomAwardId(Long strategyId);

    /**
     * 获取抽奖策略装配的随即结果
     *
     * @param strategyId 策略ID
     * @param ruleWeightValue 权重的值
     * @return 抽奖结果
     */
    Integer getRandomAwardId(Long strategyId, String ruleWeightValue);

    /**
     * 获取抽奖策略装配的随机结果
     *
     * @param key = strategyId + _ + ruleWeightValue；
     * @return 抽奖结果
     */
    Integer getRandomAwardId(String key);


    /**
     * 根据策略ID和奖品ID，扣减奖品缓存库存
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 扣减结果
     */
    Boolean subtractionAwardStock(Long strategyId, Integer awardId);

}
