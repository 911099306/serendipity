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
    /**
     * 业务异常
     */
    STRATEGY_RULE_WEIGHT_IS_NULL("ERR_BIZ_001", "业务异常，策略规则中 rule_weight 权重规则已适用但未配置"),

    /**
     * 策略未装配
     */
    UN_ASSEMBLED_STRATEGY_ARMORY("ERR_BIZ_002", "抽奖策略配置未装配，请通过IStrategyArmory完成装配"),


    ;

    private String code;
    private String info;

}
