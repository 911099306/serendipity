package com.monou.domain.award.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Serendipity
 * @description 账户状态枚举
 * @date 2024-07-26 00:02
 */
@Getter
@AllArgsConstructor
public enum AccountStatusVO {

    /**
     *
     */
    open("open", "开启"),
    close("close", "冻结"),
    ;

    private final String code;
    private final String desc;

}
