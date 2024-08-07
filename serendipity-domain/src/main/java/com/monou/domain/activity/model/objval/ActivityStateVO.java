package com.monou.domain.activity.model.objval;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Serendipity
 * @description 活动状态值对象
 * @date 2024-07-15 00:15
 */
@Getter
@AllArgsConstructor
public enum ActivityStateVO {

    /**
     *
     */
    create("create", "创建"),
    /**
     * 活动可用
     */
    open("open", "开启"),
    /**
     * 活动不可用
     */
    close("close", "关闭"),;

    private final String code;
    private final String desc;

}
