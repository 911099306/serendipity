package com.monou.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Serendipity
 * @description 规则限定枚举值
 * @date 2024-06-17 23:43
 **/
@Getter
@AllArgsConstructor
public enum RuleLimitTypeVO {
    /**
     *
     */
    EQUAL(1, "等于"),
    GT(2, "大于"),
    LT(3, "小于"),
    GE(4, "大于&等于"),
    LE(5, "小于&等于"),
    ENUM(6, "枚举"),
    ;

    private final Integer code;
    private final String info;
}
