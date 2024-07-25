package com.monou.trigger.api.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Serendipity
 * @description 抽奖策略规则，权重配置，查询N次抽奖可解锁奖品范围，应答对象
 * @date 2024-07-25 00:19
 **/
@Data
public class RaffleStrategyRuleWeightResponseDTO {

    /**
     * 权重规则配置的抽奖次数
     */
    private Integer ruleWeightCount;
    /**
     * 用户在 该活动下 完成的总抽奖次数
     */
    private Integer userActivityAccountTotalUseCount;
    /**
     * 当前权重可抽奖范围
     */
    private List<StrategyAward> strategyAwards;

    @Data
    public static class StrategyAward {
        /**
         * 奖品ID
         */
        private Integer awardId;
        /**
         * 奖品标题
         */
        private String awardTitle;
    }

}

