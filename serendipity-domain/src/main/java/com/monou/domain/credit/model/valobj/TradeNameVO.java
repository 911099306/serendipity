package com.monou.domain.credit.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Serendipity
 * @description 交易名称枚举值
 * @date 2024-07-27 00:41
 */
@Getter
@AllArgsConstructor
public enum TradeNameVO {

    /**
     *
     */
    REBATE("行为返利"),
    CONVERT_SKU("兑换抽奖"),

    ;

    private final String name;

}