package com.monou.domain.activity.respository;

import com.monou.domain.activity.model.aggregate.CreateOrderAggregate;
import com.monou.domain.activity.model.entity.ActivityCountEntity;
import com.monou.domain.activity.model.entity.ActivityEntity;
import com.monou.domain.activity.model.entity.ActivitySkuEntity;
import com.monou.domain.activity.model.objval.ActivitySkuStockKeyVO;

import java.util.Date;

/**
 * @author Serendipity
 * @description 活动仓储接口
 * @date 2024-07-15 00:43
 **/
public interface IActivityRepository {

    /**
     * 通过sku查询活动sku情况
     *
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

    /**
     * 保存sku 库存进redis
     *
     * @param cacheKey   key
     * @param stockCount 库存
     */
    void cacheActivitySkuStockCount(String cacheKey, Integer stockCount);

    /**
     * 扣减库存，并为每个库存配置分布式锁
     *
     * @param sku         商品信息
     * @param cacheKey    key
     * @param endDateTime 过期时间
     * @return 是否扣减成功
     */
    boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime);

    /**
     * 写入延迟队列，延迟消费更新库存记录
     *
     * @param activitySkuStockKeyVO sku 和 活动id
     */
    void activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO);

    /**
     * 获取活动sku库存消耗队列
     *
     * @return 活动信息
     */
    ActivitySkuStockKeyVO takeQueueValue();

    /**
     * 接收到库存请0的mq消息后，清空队列，不需继续处理
     */
    void clearQueueValue();

    /**
     * 延迟队列 + 任务趋势更新活动sku库存
     *
     * @param sku sku
     */
    void updateActivitySkuStock(Long sku);

    /**
     * 缓存库存已消耗完毕，清空数据库库存
     *
     * @param sku sku
     */
    void clearActivitySkuStock(Long sku);
}