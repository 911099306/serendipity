package com.monou.domain.activity.model.objval;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Serendipity
 * @description 订单交易类型
 * @date 2024-07-27 17:22
 **/
@Getter
@AllArgsConstructor
public enum OrderTradeTypeVO {

    /**
     *
     */
    credit_pay_trade("credit_pay_trade","积分兑换，需要支付类交易"),
    rebate_no_pay_trade("rebate_no_pay_trade", "返利奖品，不需要支付类交易"),
    ;

    private final String code;
    private final String desc;

}
