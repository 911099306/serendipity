package com.monou.domain.award.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Serendipity
 * @description 奖品状态枚举值对象 【值对象，用于描述对象属性的值，一个对象中，一个属性，有多个状态值。】
 * @date 2024-07-21 23:02
 */
@Getter
@AllArgsConstructor
public enum AwardStateVO {

    /**
     *
     */
    create("create", "创建"),
    complete("complete", "发奖完成"),
    fail("fail", "发奖失败"),
    ;

    private final String code;
    private final String desc;

}
