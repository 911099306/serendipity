package com.monou.domain.activity.respository;

import com.monou.domain.activity.model.aggregate.CreateOrderAggregate;
import com.monou.domain.activity.model.entity.ActivityCountEntity;
import com.monou.domain.activity.model.entity.ActivityEntity;
import com.monou.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @author Serendipity
 * @description 活动仓储接口
 * @date 2024-07-15 00:43
 **/
public interface IActivityRepository {

    /**
     * 通过sku查询活动sku情况
     * @param sku sku
     * @return 活动sku信息
     */
    ActivitySkuEntity queryActivitySku(Long sku);

    /**
     * 通过活动id查询活动信息
     *
     * @param activityId 活动id
     * @return 活动信息
     */
    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    /**
     * 活动次数信息查询
     *
     * @param activityCountId 活动次数id
     * @return 活动次数信息查询
     */
    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

    /**
     * 保存订单对象
     *
     * @param createOrderAggregate 聚合对象
     */
    void doSaveOrder(CreateOrderAggregate createOrderAggregate);
}
