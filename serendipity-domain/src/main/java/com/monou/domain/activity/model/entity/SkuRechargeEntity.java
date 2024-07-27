package com.monou.domain.activity.model.entity;

import com.monou.domain.activity.model.objval.OrderTradeTypeVO;
import lombok.Data;

/**
 * @author Serendipity
 * @description 活动商品充值实体对象
 * @date 2024-07-15 22:52
 **/
@Data
public class SkuRechargeEntity {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 商品SKU - activity + activity count
     */
    private Long sku;
    /**
     * 幂等业务单号，外部谁充值谁透传，这样来保证幂等（多次调用也能确保结果唯一，不会多次充值）。
     */
    private String outBusinessNo;
    /**
     * 订单类型，积分兑换的需要进行支付等操作
     */
    private OrderTradeTypeVO orderTradeType = OrderTradeTypeVO.rebate_no_pay_trade;


}
