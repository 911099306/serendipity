package com.monou.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Serendipity
 * @description 抽奖因子
 * @date 2024-06-10 22:41
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleFactorEntity {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 奖品ID
     */
    private Integer awardId;

}
