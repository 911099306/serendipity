package com.monou.domain.strategy.service.rule.tree.impl;

import com.monou.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.monou.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.monou.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Serendipity
 * @description 次数锁节点
 * @date 2024-06-17 23:39
 **/
@Slf4j
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {


        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();
    }
}
