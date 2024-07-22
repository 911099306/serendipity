package com.monou.infrastructure.persistent.dao;

import com.monou.infrastructure.persistent.po.RuleTreeNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Serendipity
 * @description  规则树节点表DAO
 * @date 2024-06-26 00:02
 **/

@Mapper
public interface IRuleTreeNodeDao {

    List<RuleTreeNode> queryRuleTreeNodeListByTreeId(String treeId);

    List<RuleTreeNode> queryRuleLocks(String[] treeIds);

}
