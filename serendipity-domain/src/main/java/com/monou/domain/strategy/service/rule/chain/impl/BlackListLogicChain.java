package com.monou.domain.strategy.service.rule.chain.impl;

import com.monou.domain.strategy.respository.IStrategyRepository;
import com.monou.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.monou.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.monou.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Serendipity
 * @description 黑名单责任链
 * @date 2024-06-17 22:18
 **/
@Slf4j
@Component("rule_blacklist")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BlackListLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyRepository repository;


    /**
     * 进行黑名单过滤，若位于黑名单则直接返回兜底奖品，否则交由下面的链路处理
     * @param userId     用户id
     * @param strategyId 策略id
     * @return 奖品id
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {

        log.info("抽奖责任链-黑名单开始 userId:{} strategyId:{} ruleModel:{}", userId, strategyId, ruleModel());

        // 查询规则值配置
        String ruleValue = repository.queryStrategyRuleValue(strategyId, ruleModel());
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.parseInt(splitRuleValue[0]);

        // 黑名单抽奖判断
        String[] userBlackIds = splitRuleValue[1].split(Constants.SPLIT);
        for (String userBlackId : userBlackIds) {
            if (userId.equals(userBlackId)) {
                log.info("抽奖责任链-黑名单接管 userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
                return DefaultChainFactory.StrategyAwardVO.builder()
                        .awardId(awardId)
                        .logicModel(ruleModel())
                        // 写入默认配置黑名单奖品值 0.01 ~ 1 积分，也可以配置到数据库表中
                        .awardRuleValue("0.01,1")
                        .build();
            }
        }

        // 过滤其他责任链
        log.info("抽奖责任链-黑名单放行 userId:{} strategyId:{} ruleModel:{}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);
    }

    @Override
    protected String ruleModel() {
        return  DefaultChainFactory.LogicModel.RULE_BLACKLIST.getCode();
    }
}
