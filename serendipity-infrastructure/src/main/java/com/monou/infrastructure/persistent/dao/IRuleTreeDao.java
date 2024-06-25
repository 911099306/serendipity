package com.monou.infrastructure.persistent.dao;

import com.monou.infrastructure.persistent.po.RuleTree;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Serendipity
 * @description
 * @date 2024-06-26 00:01
 **/
@Mapper
public interface IRuleTreeDao {
    RuleTree queryRuleTreeByTreeId(String treeId);
}
