package com.monou.domain.rebate.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Serendipity
 * @description 返利类型（sku 活动库存充值商品、integral 用户活动积分）
 * @date 2024-07-24 23:46
 */
@Getter
@AllArgsConstructor
public enum RebateTypeVO {

    /**
     *
     */
    SKU("sku", "活动库存充值商品"),
    INTEGRAL("integral", "用户活动积分"),
    ;

    private final String code;
    private final String info;
}
