package com.monou.domain.strategy.service.rule.tree.factory;

import com.monou.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.monou.domain.strategy.model.valobj.RuleTreeVO;
import com.monou.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.monou.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import com.monou.domain.strategy.service.rule.tree.factory.engine.impl.DecisionTreeEngine;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Serendipity
 * @description 规则工厂
 * @date 2024-06-17 23:14
 **/
@Service
@RequiredArgsConstructor
public class DefaultTreeFactory {

    private final Map<String, ILogicTreeNode> logicTreeNodeGroup;

    public IDecisionTreeEngine openLogicTree(RuleTreeVO ruleTreeVO) {
        return new DecisionTreeEngine(logicTreeNodeGroup, ruleTreeVO);
    }


    /**
     * 决策树个动作实习
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TreeActionEntity {
        private RuleLogicCheckTypeVO ruleLogicCheckType;
        private StrategyAwardVO strategyAwardVO;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /**
         * 抽奖奖品ID - 内部流转使用
         */
        private Integer awardId;
        /**
         * 抽奖奖品规则
         */
        private String awardRuleValue;
    }


}
