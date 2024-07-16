package com.monou.domain.activity.service;

import com.monou.domain.activity.model.objval.ActivitySkuStockKeyVO;

/**
 * @author Serendipity
 * @description 活动sku库存处理接口
 * @date 2024-07-17 00:28
 **/
public interface ISkuStock {

    /**
     * 获取活动sku库存消耗队列
     *
     * @return 奖品库存Key信息
     * @throws InterruptedException 异常
     */
    ActivitySkuStockKeyVO takeQueueValue() throws InterruptedException;

    /**
     * 接收到库存请0的mq消息后，清空队列，不需继续处理
     */
    void clearQueueValue();

    /**
     * 延迟队列 + 任务趋势更新活动sku库存
     *
     * @param sku 活动商品
     */
    void updateActivitySkuStock(Long sku);

    /**
     * 缓存库存已消耗完毕，清空数据库库存
     *
     * @param sku 活动商品
     */
    void clearActivitySkuStock(Long sku);
}
