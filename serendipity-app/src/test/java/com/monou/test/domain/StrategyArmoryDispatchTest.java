package com.monou.test.domain;

import com.monou.domain.strategy.service.armory.IStrategyArmory;
import com.monou.domain.strategy.service.armory.IStrategyDispatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author serendipity
 * @description
 * @version 1.0
 * @date 2024/6/3
 **/

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyArmoryDispatchTest {

    @Resource
    private IStrategyArmory strategyArmory;

    @Resource
    private IStrategyDispatch strategyDispatch;

    @Before
    public void test_strategyArmory(){
        strategyArmory.assembleLotteryStrategy(100001L);
    }

    @Test
    public void test_getRandomAwardId(){
        log.info("测试结果：{} - 奖品ID", strategyDispatch.getRandomAwardId(100001L));
    }

    @Test
    public void test_getRandomAwardId_ruleWeightValue(){
        log.info("测试结果：{} - 4000 奖品ID", strategyDispatch.getRandomAwardId(100001L, "4000:102,103,104,105"));
        log.info("测试结果：{} - 5000 奖品ID", strategyDispatch.getRandomAwardId(100001L, "5000:102,103,104,105,106,107"));
        log.info("测试结果：{} - 6000 奖品ID", strategyDispatch.getRandomAwardId(100001L, "6000:102,103,104,105,106,107,108,109"));
    }



}
