package com.monou.domain.strategy.service.rule.tree.factory.engine;

import com.monou.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

import java.util.Date;

/**
 * @author Serendipity
 * @description 引擎接口规则树组合接口
 * @date 2024-06-17 23:53
 **/
public interface IDecisionTreeEngine {

    DefaultTreeFactory.StrategyAwardVO process(String userId, Long strategyId, Integer awardId, Date endDateTime);

}
