package com.monou.domain.strategy.respository;

import com.monou.domain.strategy.model.entity.StrategyAwardEntity;

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

    void storeStrategyAwardSearchRateTable(Long strategyId, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable);

    int getRateRange(Long strategyId);

    Integer getStrategyAwardAssmble(Long strategyId, int rateKey);
}