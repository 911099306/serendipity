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
     * 唯一索引冲突
     */
    INDEX_DUP("0003", "唯一索引冲突"),
    /**
     * 业务异常
     */
    STRATEGY_RULE_WEIGHT_IS_NULL("ERR_BIZ_001", "业务异常，策略规则中 rule_weight 权重规则已适用但未配置"),

    /**
     * 策略未装配
     */
    UN_ASSEMBLED_STRATEGY_ARMORY("ERR_BIZ_002", "抽奖策略配置未装配，请通过IStrategyArmory完成装配"),
    /**
     * 活动未开启
     */
    ACTIVITY_STATE_ERROR("ERR_BIZ_003", "活动未开启（非open状态）"),
    /**
     * 非活动开启日期范围
     */
    ACTIVITY_DATE_ERROR("ERR_BIZ_004", "非活动日期范围"),
    /**
     * 活动库存不足
     */
    ACTIVITY_SKU_STOCK_ERROR("ERR_BIZ_005", "活动库存不足"),

    /**
     * 账户总额度不足
     */
    ACCOUNT_QUOTA_ERROR("ERR_BIZ_006","账户总额度不足"),
    /**
     * 账户月额度不足
     */
    ACCOUNT_MONTH_QUOTA_ERROR("ERR_BIZ_007","账户月额度不足"),
    /**
     * 账户日额度不足
     */
    ACCOUNT_DAY_QUOTA_ERROR("ERR_BIZ_008","账户日额度不足"),

    ;

    private String code;
    private String info;

}
