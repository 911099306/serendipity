package com.monou.infrastructure.persistent.dao;

import com.monou.domain.strategy.model.entity.StrategyEntity;
import com.monou.infrastructure.persistent.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author serendipity
 * @description 抽奖策略 DAO
 * @date 2024-06-02
 */
@Mapper
public interface IStrategyDao {

    List<Strategy> queryStrategyList();

    Strategy queryStrategyEntityByStrategyId(Long strategyId);
}
