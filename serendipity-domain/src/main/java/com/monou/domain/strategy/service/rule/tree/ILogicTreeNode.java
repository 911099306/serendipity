package com.monou.domain.strategy.service.rule.tree;

import com.monou.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author Serendipity
 * @description 规则树接口
 * @date 2024-06-17 23:39
 **/
public interface ILogicTreeNode {


    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId);

}
