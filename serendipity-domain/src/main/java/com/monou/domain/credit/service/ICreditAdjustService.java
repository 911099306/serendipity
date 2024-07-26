package com.monou.domain.credit.service;

import com.monou.domain.credit.model.entity.TradeEntity;

/**
 * @author Serendipity
 * @description 积分调额接口【正逆向，增减积分】
 * @date 2024-07-27 00:42
 */
public interface ICreditAdjustService {

    /**
     * 创建增加积分额度订单
     *
     * @param tradeEntity 交易实体对象
     * @return 单号
     */
    String createOrder(TradeEntity tradeEntity);

}
