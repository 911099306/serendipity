package com.monou.domain.rebate.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Serendipity
 * @description 任务状态值对象
 * @date 2024-07-24 22:15
 */
@Getter
@AllArgsConstructor
public enum TaskStateVO {

    create("create", "创建"),
    complete("complete", "发送完成"),
    fail("fail", "发送失败"),
            ;

    private final String code;
    private final String desc;

}