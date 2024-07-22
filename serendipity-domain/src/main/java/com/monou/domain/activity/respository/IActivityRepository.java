package com.monou.domain.activity.respository;

import com.monou.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import com.monou.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.monou.domain.activity.model.entity.*;
import com.monou.domain.activity.model.objval.ActivitySkuStockKeyVO;

import java.util.Date;
import java.util.List;

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
    void doSaveOrder(CreateQuotaOrderAggregate createOrderAggregate);

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


    /**
     * 查询未被使用的订单信息
     *
     * @param partakeRaffleActivityEntity 参与抽奖活动实体对象
     * @return 未被使用的订单
     */
    UserRaffleOrderEntity queryNoUsedRaffleOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);

    /**
     * 查询总账户总额度
     *
     * @param userId 用户ID
     * @param activityId 活动ID
     * @return 账户额度记录
     */
    ActivityAccountEntity queryActivityAccountByUserId(String userId, Long activityId);

    /**
     * 查询月账户额度信息
     * @param userId 用户ID
     * @param activityId 活动ID
     * @param month 月份
     * @return 月额度信息
     */
    ActivityAccountMonthEntity queryActivityAccountMonthByUserId(String userId, Long activityId, String month);

    /**
     * 查询日账户额度信息
     * @param userId 用户ID
     * @param activityId 活动ID
     * @param day 日
     * @return 日额度信息
     */
    ActivityAccountDayEntity queryActivityAccountDayByUserId(String userId, Long activityId, String day);

    /**
     * 保存抽奖单聚合实体对象
     *
     * @param createPartakeOrderAggregate 抽奖单聚合实体对象
     */
    void saveCreatePartakeOrderAggregate(CreatePartakeOrderAggregate createPartakeOrderAggregate);

    /**
     * 根据活动id查询活动Sku信息列表
     * @param activityId 活动id
     * @return 活动ksu列表
     */
    List<ActivitySkuEntity> queryActivitySkuListByActivityId(Long activityId);


    /**
     * 查询当前用户在当前活动今天抽奖几次
     *
     * @param activityId 活动ID
     * @param userId 用户ID
     * @return 抽奖次数
     */
    Integer queryRaffleActivityAccountDayPartakeCount(Long activityId, String userId);


}
