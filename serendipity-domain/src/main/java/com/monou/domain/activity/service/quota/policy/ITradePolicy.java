package com.monou.domain.activity.service.quota.policy;

import com.monou.domain.activity.model.aggregate.CreateQuotaOrderAggregate;

/**
 * @author Serendipity
 * @description 交易策略接口，包括；返利兑换（不用支付），积分订单（需要支付）
 * @date 2024-07-27 17:28
 */
public interface ITradePolicy {

    /**
     * 进行不同类型的交易支付操作
     *
     * @param createQuotaOrderAggregate 交易订单
     */
    void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate);

}