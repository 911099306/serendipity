package com.monou.domain.activity.service.rule;

import com.monou.domain.activity.model.entity.ActivityCountEntity;
import com.monou.domain.activity.model.entity.ActivityEntity;
import com.monou.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @author Serendipity
 * @description 下单规则过滤接口
 * @date 2024-07-15 23:05
 **/
public interface IActionChain extends IActionChainArmory {

    /**
     * 执行责任链过滤郭泽
     * @param activitySkuEntity     活动配置信息
     * @param activityEntity        活动信息
     * @param activityCountEntity   活动剩余参与次数信息
     * @return  是否可以参与活动
     */
    boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);
}
