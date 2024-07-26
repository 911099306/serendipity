package com.monou.domain.credit.repository;

import com.monou.domain.credit.model.aggregate.TradeAggregate;

/**
 * @author Serendipity
 * @description 用户积分仓储
 * @date 2024-07-27 00:41
 **/
public interface ICreditRepository {

    /**
     * 更改用户积分并保存流水
     *
     * @param tradeAggregate 聚合对象 [积分订单实体，积分账户实体，用户id]
     */
    void saveUserCreditTradeOrder(TradeAggregate tradeAggregate);

}
