package com.monou.infrastructure.persistent.dao;


import com.monou.domain.strategy.model.entity.StrategyAwardEntity;
import com.monou.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;



/**
 * @author serendipity
 * @description 抽奖策略奖品明细配置 - 概率、规则 DAO
 * @date 2024-06-02
 */
@Mapper
public interface IStrategyAwardDao {

    List<StrategyAward> queryStrategyAwardList();

    List<StrategyAward> queryStrategyAwardListByStrategyId(Long strategyId);

    String queryStrategyAwardRuleModels(StrategyAward strategyAward);

    void updateStrategyAwardStock(StrategyAward strategyAward);


    StrategyAward queryStrategyAward(StrategyAward strategyAwardReq);
}
