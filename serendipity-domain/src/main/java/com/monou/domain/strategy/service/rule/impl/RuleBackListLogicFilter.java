package com.monou.domain.strategy.service.rule.impl;

import com.monou.domain.strategy.model.entity.RuleActionEntity;
import com.monou.domain.strategy.model.entity.RuleMatterEntity;
import com.monou.domain.strategy.model.entity.StrategyRuleEntity;
import com.monou.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.monou.domain.strategy.respository.IStrategyRepository;
import com.monou.domain.strategy.service.annotation.LogicStrategy;
import com.monou.domain.strategy.service.rule.ILogicFilter;
import com.monou.domain.strategy.service.rule.factory.DefaultLogicFactory;
import com.monou.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Serendipity
 * @description 【抽奖前规则】黑名单用户过滤规则
 * @date 2024-06-10 23:12
 **/
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_BLACKLIST)
public class RuleBackListLogicFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {

    @Resource
    private IStrategyRepository repository;

    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤-黑名单 userId:{}, strategyId:{}, ruleModel:{}", ruleMatterEntity.getUserId(), ruleMatterEntity.getStrategyId(),ruleMatterEntity.getRuleModel());

        String userId = ruleMatterEntity.getUserId();

        String ruleValue =  repository.queryStrategyRuleVale(ruleMatterEntity.getStrategyId(), ruleMatterEntity.getAwardId(), ruleMatterEntity.getRuleModel());
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.parseInt(splitRuleValue[0]);

        // 过滤其他规则
        String[] userBlackIds = splitRuleValue[1].split(Constants.SPLIT);
        for (String userBlackId : userBlackIds) {
            if (userId.equals(userBlackId)) {
                return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                        .ruleModel(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode())
                        .data(RuleActionEntity.RaffleBeforeEntity.builder()
                                .strategyId(ruleMatterEntity.getStrategyId())
                                .awardId(awardId)
                                .build())
                        .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                        .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                        .build();
            }
        }

        return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                .build();
    }
}
