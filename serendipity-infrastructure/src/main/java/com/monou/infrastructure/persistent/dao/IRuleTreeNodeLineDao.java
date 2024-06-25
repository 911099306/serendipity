package com.monou.infrastructure.persistent.dao;

import com.monou.infrastructure.persistent.po.RuleTreeNodeLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Serendipity
 * @description  规则树节点连线表DAO
 * @date 2024-06-26 00:02
 **/
@Mapper
public interface IRuleTreeNodeLineDao {

    List<RuleTreeNodeLine> queryRuleTreeNodeLineListByTreeId(String treeId);
}
