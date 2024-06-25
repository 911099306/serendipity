package com.monou.domain.strategy.service.rule.chain.factory;

import com.monou.domain.strategy.model.entity.StrategyEntity;
import com.monou.domain.strategy.respository.IStrategyRepository;
import com.monou.domain.strategy.service.rule.chain.ILogicChain;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Serendipity
 * @description 工厂
 * @date 2024-06-17 22:38
 **/
@Service
@RequiredArgsConstructor
public class DefaultChainFactory {

    private final Map<String, ILogicChain> logicChainGroup;

    private final IStrategyRepository repository;

    /**
     * 通过策略id，构建责任链
     *
     * @param strategyId 策略ID
     * @return LogicChain
     */
    public ILogicChain openLogicChain(Long strategyId) {

        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);

        String[] ruleModels = strategy.ruleModels();

        ILogicChain defaultLogicChain = logicChainGroup.get("default");

        if (ruleModels == null || ruleModels.length == 0) {
            return defaultLogicChain;
        }

        ILogicChain logicChain = logicChainGroup.get(ruleModels[0]);
        ILogicChain current = logicChain;
        for (int i = 1; i < ruleModels.length; i++) {
            current = current.appendNext(logicChainGroup.get(ruleModels[i]));
        }

        // 责任链的最后装填默认责任链
        current.appendNext(defaultLogicChain);

        return logicChain;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /**  */
        private String logicModel;
    }

    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        /**
         *
         */
        RULE_DEFAULT("rule_default", "默认抽奖"),
        RULE_BLACKLIST("rule_blacklist", "黑名单抽奖"),
        RULE_WEIGHT("rule_weight", "权重规则"),
        ;

        private final String code;
        private final String info;

    }


}
