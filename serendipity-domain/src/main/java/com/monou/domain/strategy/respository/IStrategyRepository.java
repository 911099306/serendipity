package com.monou.domain.strategy.respository;

import com.monou.domain.strategy.model.entity.StrategyAwardEntity;
import com.monou.domain.strategy.model.entity.StrategyEntity;
import com.monou.domain.strategy.model.entity.StrategyRuleEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author serendipity
 * @description 策略仓储接口
 * @version 1.0
 * @date 2024/6/3
 **/
public interface IStrategyRepository {

    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    void storeStrategyAwardSearchRateTable(String key, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable);

    int getRateRange(Long strategyId);

    int getRateRange(String key);


    Integer getStrategyAwardAssmble(String key, int rateKey);

    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);

    String queryStrategyRuleVale(Long strategyId, Integer awardId, String ruleModel);
}
