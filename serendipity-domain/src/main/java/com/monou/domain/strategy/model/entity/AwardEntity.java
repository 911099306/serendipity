package com.monou.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author serendipity
 * @description 策略结果实体
 * @version 1.0
 * @date 2024/6/3
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AwardEntity {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 奖品ID
     */
    private Integer awardId;
}
