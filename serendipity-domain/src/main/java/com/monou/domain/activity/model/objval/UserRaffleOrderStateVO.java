package com.monou.domain.activity.model.objval;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Serendipity
 * @description
 * @date 2024-07-19 21:45
 **/
@Getter
@AllArgsConstructor
public enum UserRaffleOrderStateVO {
    /**
     * 创建
     */
    create("create", "创建"),
    /**
     * 已使用
     */
    used("used", "已使用"),
    /**
     * 作废
     */
    cancel("cancel", "已作废"),
            ;

    private final String code;
    private final String desc;

}
