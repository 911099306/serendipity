package com.monou.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.monou.infrastructure.persistent.dao.IRuleTreeNodeDao;
import com.monou.infrastructure.persistent.po.RuleTreeNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Serendipity
 * @description 规则单元测试
 * @date 2024-07-22 22:17
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleTreeNodeDaoTest {

    @Resource
    private IRuleTreeNodeDao ruleTreeNodeDao;

    @Test
    public void test_queryRuleLocks() {
        List<RuleTreeNode> ruleTreeNodes = ruleTreeNodeDao.queryRuleLocks(new String[]{"tree_lock_1", "tree_lock_2"});
        log.info("测试结果:{}", JSON.toJSONString(ruleTreeNodes));
    }

}
