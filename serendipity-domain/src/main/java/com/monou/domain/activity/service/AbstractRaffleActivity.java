package com.monou.domain.activity.service;

import com.alibaba.fastjson.JSON;
import com.monou.domain.activity.model.entity.*;
import com.monou.domain.activity.respository.IActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Serendipity
 * @description 抽奖活动抽象类，定义标准的流程
 * @date 2024-07-15 00:51
 **/
@Slf4j
public abstract class AbstractRaffleActivity implements IRaffleOrder {


    // 为什么 Spring 推荐使用构造注入；https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html\
    protected IActivityRepository activityRepository;

    public AbstractRaffleActivity(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public ActivityOrderEntity createRaffleActivityOrder(ActivityShopCartEntity activityShopCartEntity) {
        // 1. 通过sku查询活动信息
        ActivitySkuEntity activitySkuEntity = activityRepository.queryActivitySku(activityShopCartEntity.getSku());
        // 2. 查询活动信息
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        // 3. 查询次数信息（用户在活动上可参与的次数）
        ActivityCountEntity activityCountEntity = activityRepository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        log.info("查询结果：{} {} {}", JSON.toJSONString(activitySkuEntity), JSON.toJSONString(activityEntity), JSON.toJSONString(activityCountEntity));

        return ActivityOrderEntity.builder().build();
    }

}
