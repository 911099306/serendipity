package com.monou.domain.strategy.service;

import com.monou.domain.strategy.model.entity.RaffleAwardEntity;
import com.monou.domain.strategy.model.entity.RaffleFactorEntity;
import com.monou.domain.strategy.model.entity.RuleActionEntity;
import com.monou.domain.strategy.model.entity.StrategyEntity;
import com.monou.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.monou.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import com.monou.domain.strategy.respository.IStrategyRepository;
import com.monou.domain.strategy.service.IRaffleStrategy;
import com.monou.domain.strategy.service.armory.IStrategyDispatch;
import com.monou.domain.strategy.service.rule.chain.ILogicChain;
import com.monou.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.monou.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import com.monou.types.enums.ResponseCode;
import com.monou.types.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Serendipity
 * @description 抽奖策略抽象类，定义抽奖的标准流程 -> 定义模拟板，提供抽象方法
 * @date 2024-06-10 22:45
 **/
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {

    /**
     * 策略仓储服务 -> domain层像一个大厨，仓储层提供米面粮油
     */
    private final IStrategyRepository repository;
    /**
     * 策略调度服务 -> 只负责抽奖处理，通过新增接口的方式，隔离职责，不需要使用方关心或者调用抽奖的初始化
     */
    private final IStrategyDispatch strategyDispatch;

    /**
     * 抽奖的责任链 -> 从抽奖的规则中，解耦出前置规则为责任链处理
     */
    private final DefaultChainFactory defaultChainFactory;

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {

        // 1. 参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (null == strategyId || StringUtils.isBlank(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2. 通过责任链进行过滤
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);

        Integer awardId = logicChain.logic(userId, strategyId);

        // 4. 查询奖品规则「抽奖中（拿到奖品ID时，过滤规则）、抽奖后（扣减完奖品库存后过滤，抽奖中拦截和无库存则走兜底）」
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModelVO(strategyId, awardId);

        // 5. 抽奖中 - 规则过滤
        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> ruleActionCenterEntity = this.doCheckRaffleCenterLogic(RaffleFactorEntity.builder()
                .userId(userId)
                .strategyId(strategyId)
                .awardId(awardId)
                .build(), strategyAwardRuleModelVO.raffleCenterRuleModelList());

        if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionCenterEntity.getCode())){
            log.info("【临时日志】中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励。");
            return RaffleAwardEntity.builder()
                    .awardDesc("中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励。")
                    .build();
        }

        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .build();
    }


    protected abstract RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logics);

}
