package com.monou.domain.strategy.service.rule.filter;

import com.monou.domain.strategy.model.entity.RuleActionEntity;
import com.monou.domain.strategy.model.entity.RuleMatterEntity;

/**
 * @author Serendipity
 * @description
 * @date 2024-06-10 22:49
 **/
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {

    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);
}
