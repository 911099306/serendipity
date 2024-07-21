package com.monou.test.infrastructure;

import com.monou.infrastructure.persistent.dao.IStrategyAwardDao;
import com.monou.infrastructure.persistent.po.StrategyAward;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Serendipity
 * @description
 * @date 2024-07-22 01:30
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class IStrategyAwardDaoTest {

    @Resource
    private IStrategyAwardDao strategyAwardDao;

    @Test
    public void test_queryStrategyAward() {
        StrategyAward strategyAwardReq = new StrategyAward();
        strategyAwardReq.setStrategyId(100006L);
        strategyAwardReq.setAwardId(104);
        StrategyAward strategyAward = strategyAwardDao.queryStrategyAward(strategyAwardReq);
        System.out.println("strategyAward = " + strategyAward);
    }
}
