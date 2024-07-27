package com.monou.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Serendipity
 * @description 抽奖请求参数
 * @date 2024-07-02 23:42
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleStrategyRequestDTO implements Serializable {

    /**
     * 抽奖策略ID
     */
    private Long strategyId;

}
