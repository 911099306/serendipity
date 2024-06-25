package com.monou.test.infrastructure;

import com.alibaba.fastjson2.JSON;
import com.monou.domain.strategy.model.valobj.RuleTreeVO;
import com.monou.domain.strategy.respository.IStrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Serendipity
 * @description 策略仓储测试
 * @date 2024-06-26 00:07
 **/

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyRepositoryTest {

    @Resource
    private IStrategyRepository strategyRepository;

    @Test
    public void queryRuleTreeVOByTreeId() {
        RuleTreeVO ruleTreeVO = strategyRepository.queryRuleTreeVOByTreeId("tree_lock");
        log.info("测试结果：{}", JSON.toJSONString(ruleTreeVO));
    }
}
