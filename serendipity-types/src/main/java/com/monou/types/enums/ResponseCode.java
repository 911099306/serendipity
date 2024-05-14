package com.monou.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * @author serendipity
 * @version 1.0
 * @date 2024/5/15
 **/


@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseCode {

    /**
     * 成功
     */
    SUCCESS("0000", "成功"),
    /**
     * 失败
     */
    UN_ERROR("0001", "未知失败"),
    /**
     * 非法参数
     */
    ILLEGAL_PARAMETER("0002", "非法参数"),
    ;

    private String code;
    private String info;

}
