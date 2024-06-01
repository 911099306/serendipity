package com.monou.infrastructure.persistent.dao;

import com.monou.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author serendipity
 * @description 策略规则 DAO
 * @date 2024-06-02
 */
@Mapper
public interface IStrategyRuleDao {

    List<StrategyRule> queryStrategyRuleList();

}
