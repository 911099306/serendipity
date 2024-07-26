package com.monou.domain.credit.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Serendipity
 * @description 交易类型枚举值
 * @date 2024-07-27 00:41
 **/
@Getter
@AllArgsConstructor
public enum TradeTypeVO {


    /**
     *
     */
    FORWARD("forward", "正向交易，+ 积分"),
    REVERSE("reverse", "逆向交易，- 积分"),

    ;

    private final String code;
    private final String info;

}