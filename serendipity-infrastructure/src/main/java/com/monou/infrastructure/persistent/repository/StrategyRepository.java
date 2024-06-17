package com.monou.infrastructure.persistent.repository;

import com.monou.domain.strategy.model.entity.StrategyAwardEntity;
import com.monou.domain.strategy.model.entity.StrategyEntity;
import com.monou.domain.strategy.model.entity.StrategyRuleEntity;
import com.monou.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import com.monou.domain.strategy.respository.IStrategyRepository;
import com.monou.infrastructure.persistent.dao.IStrategyAwardDao;
import com.monou.infrastructure.persistent.dao.IStrategyDao;
import com.monou.infrastructure.persistent.dao.IStrategyRuleDao;
import com.monou.infrastructure.persistent.po.Strategy;
import com.monou.infrastructure.persistent.po.StrategyAward;
import com.monou.infrastructure.persistent.po.StrategyRule;
import com.monou.infrastructure.persistent.redis.IRedisService;
import com.monou.types.common.Constants;
import org.checkerframework.checker.units.qual.K;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author serendipity
 * @version 1.0
 * @description 策略仓储实现
 * @date 2024/6/3
 **/
@Repository
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IStrategyDao strategyDao;
    @Resource
    private IStrategyRuleDao strategyRuleDao;
    @Resource
    private IStrategyAwardDao strategyAwardDao;
    @Resource
    private IRedisService redisService;

    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        // 从redis中获取 key
        List<StrategyAwardEntity> strategyAwardEntities = redisService.getValue(cacheKey);
        if (strategyAwardEntities != null && !strategyAwardEntities.isEmpty()) {
            return strategyAwardEntities;
        }
        // 从数据库中读取数据
        List<StrategyAward> strategyAwards = strategyAwardDao.queryStrategyAwardListByStrategyId(strategyId);
        strategyAwardEntities = new ArrayList<>(strategyAwards.size());
        // 赋值
        for (StrategyAward strategyAward : strategyAwards) {
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder()
                    .strategyId(strategyAward.getStrategyId())
                    .awardId(strategyAward.getAwardId())
                    .awardCount(strategyAward.getAwardCount())
                    .awardCountSurplus(strategyAward.getAwardCountSurplus())
                    .awardRate(strategyAward.getAwardRate()).build();
            strategyAwardEntities.add(strategyAwardEntity);
        }
        redisService.setValue(cacheKey, strategyAwardEntities);
        return strategyAwardEntities;
    }

    @Override
    public void storeStrategyAwardSearchRateTable(String key, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable) {
        // 1. 存储抽奖策略范围值，如10000，用于生成1000以内的随机数
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key, rateRange);
        // 2. 存储概率查找表
        Map<Integer, Integer> cacheRateTable = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key);
        cacheRateTable.putAll(strategyAwardSearchRateTable);

    }

    @Override
    public int getRateRange(Long strategyId) {
        return getRateRange(String.valueOf(strategyId));
    }

    @Override
    public int getRateRange(String key) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key);
    }

    @Override
    public Integer getStrategyAwardAssmble(String key, int rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key, rateKey);

    }

    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        // 优先从缓存中获取
        String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        StrategyEntity strategyEntity = redisService.getValue(cacheKey);
        if (strategyEntity != null) {
            return strategyEntity;
        }
        // 缓存中没有，数据库中查询
        Strategy strategy = strategyDao.queryStrategyEntityByStrategyId(strategyId);
        if (null == strategy) {
            return StrategyEntity.builder().build();
        }
        strategyEntity = StrategyEntity.builder().strategyId(strategy.getStrategyId()).strategyDesc(strategy.getStrategyDesc()).ruleModels(strategy.getRuleModels()).build();
        // 加入缓存
        redisService.setValue(cacheKey, strategyEntity);
        return strategyEntity;
    }

    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel) {
        // 优先从缓存中获取
        // String cacheKey = Constants.RedisKey.STRATEGY_RULE_KEY + strategyId +"_" + ruleModel;
        // StrategyRuleEntity strategyRuleEntity = redisService.getValue(cacheKey);
        // if (strategyRuleEntity != null) {
        //     return strategyRuleEntity;
        // }
        // 缓存中没有， 从数据库中给查询
        StrategyRule strategyRuleReq = new StrategyRule();
        strategyRuleReq.setStrategyId(strategyId);
        strategyRuleReq.setRuleModel(ruleModel);
        StrategyRule strategyRuleRes = strategyRuleDao.queryStrategyRule(strategyRuleReq);
        StrategyRuleEntity strategyRuleEntity = StrategyRuleEntity.builder().strategyId(strategyRuleRes.getStrategyId()).awardId(strategyRuleRes.getAwardId()).ruleType(strategyRuleRes.getRuleType()).ruleModel(strategyRuleRes.getRuleModel()).ruleValue(strategyRuleRes.getRuleValue()).ruleDesc(strategyRuleRes.getRuleDesc()).build();

        // 加入缓存
        // redisService.setValue(cacheKey, strategyRuleEntity);
        return strategyRuleEntity;
    }

    @Override
    public String queryStrategyRuleVale(Long strategyId, Integer awardId, String ruleModel) {
        StrategyRule strategyRule = new StrategyRule();
        strategyRule.setStrategyId(strategyId);
        strategyRule.setAwardId(awardId);
        strategyRule.setRuleModel(ruleModel);
        return strategyRuleDao.queryStrategyRuleValue(strategyRule);
    }

    @Override
    public String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel) {
        StrategyRule strategyRule = new StrategyRule();
        strategyRule.setStrategyId(strategyId);
        strategyRule.setAwardId(awardId);
        strategyRule.setRuleModel(ruleModel);
        return strategyRuleDao.queryStrategyRuleValue(strategyRule);
    }

    @Override
    public StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId) {
        StrategyAward strategyAward = new StrategyAward();
        strategyAward.setStrategyId(strategyId);
        strategyAward.setAwardId(awardId);
        String ruleModels = strategyAwardDao.queryStrategyAwardRuleModels(strategyAward);
        return StrategyAwardRuleModelVO.builder().ruleModels(ruleModels).build();
    }
}
