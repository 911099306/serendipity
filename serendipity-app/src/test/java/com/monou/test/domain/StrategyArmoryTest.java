package com.monou.test.domain;

import com.monou.domain.strategy.service.armory.IStrategyArmory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
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
public class StrategyArmoryTest {

    @Resource
    private IStrategyArmory strategyArmory;

    @Test
    public void test_strategyArmory(){
        strategyArmory.assembleLotteryStrategy(100002L);
    }

    @Test
    public void test_strategyRandomVal(){

        for (int i = 0; i < 100; i++) {

            log.info("测试结果：{} - 奖品ID", strategyArmory.getRandomAwardId(100002L));

        }

    }

}
