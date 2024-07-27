package com.monou.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Serendipity
 * @description 未完成支付的活动单
 * @date 2024-07-27 21:48
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnpaidActivityOrderEntity {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 外部透传ID
     */
    private String outBusinessNo;
    /**
     * 订单金额
     */
    private BigDecimal payAmount;

}

