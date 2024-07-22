package com.monou.domain.strategy.service.rule.tree.factory.engine.impl;

import com.monou.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.monou.domain.strategy.model.valobj.RuleTreeNodeLineVO;
import com.monou.domain.strategy.model.valobj.RuleTreeNodeVO;
import com.monou.domain.strategy.model.valobj.RuleTreeVO;
import com.monou.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.monou.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.monou.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Serendipity
 * @description 决策树引擎
 * @date 2024-06-17 23:53
 **/
@Slf4j
@RequiredArgsConstructor
public class DecisionTreeEngine  implements IDecisionTreeEngine {

    private final Map<String, ILogicTreeNode> logicTreeNodeGroup;

    private final RuleTreeVO ruleTreeVO;

    @Override
    public DefaultTreeFactory.StrategyAwardVO process(String userId, Long strategyId, Integer awardId, Date endDateTime) {
        DefaultTreeFactory.StrategyAwardVO strategyAwardData = null;

        // 获取基础信息
        String nextNode = ruleTreeVO.getTreeRootRuleNode();
        Map<String, RuleTreeNodeVO> treeNodeMap = ruleTreeVO.getTreeNodeMap();

        // 获取起始节点「根节点记录了第一个要执行的规则」
        RuleTreeNodeVO ruleTreeNode = treeNodeMap.get(nextNode);
        while (nextNode != null) {
            // 获取决策节点
            ILogicTreeNode logicTreeNode = logicTreeNodeGroup.get(ruleTreeNode.getRuleKey());
            String ruleValue = ruleTreeNode.getRuleValue();

            // 进行判断规则
            DefaultTreeFactory.TreeActionEntity logicEntity = logicTreeNode.logic(userId, strategyId, awardId, ruleValue, endDateTime);
            RuleLogicCheckTypeVO ruleLogicCheckTypeVO = logicEntity.getRuleLogicCheckType();
            strategyAwardData = logicEntity.getStrategyAwardVO();
            log.info("决策树引擎【{}】treeId:{} node:{} code:{}", ruleTreeVO.getTreeName(), ruleTreeVO.getTreeId(), nextNode, ruleLogicCheckTypeVO.getCode());

            // 获取下个节点
            nextNode = nextNode(ruleLogicCheckTypeVO.getCode(), ruleTreeNode.getTreeNodeLineVOList());
            ruleTreeNode = treeNodeMap.get(nextNode);

        }

        // 返回最终结果
        return strategyAwardData;
    }

    private String nextNode(String matteralue, List<RuleTreeNodeLineVO> ruleTreeNodeLineVOList) {
        if (ruleTreeNodeLineVOList == null || ruleTreeNodeLineVOList.isEmpty()) {
            return null;
        }
        for (RuleTreeNodeLineVO nodeLine : ruleTreeNodeLineVOList) {
            if (decisionLogic(matteralue, nodeLine)) {
                return nodeLine.getRuleNodeTo();
            }
        }
        return null;
    }

    public boolean decisionLogic(String matterValue, RuleTreeNodeLineVO nodeLine) {
        switch (nodeLine.getRuleLimitType()) {
            case EQUAL:
                return matterValue.equals(nodeLine.getRuleLimitValue().getCode());
            // 以下规则暂时不需要实现
            case GT:
            case LT:
            case GE:
            case LE:
            default:
                return false;
        }
    }
}
