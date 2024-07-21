package com.monou.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Serendipity
 * @description 抽奖列表请求对象
 * @date 2024-07-02 23:40
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleAwardListRequestDTO {

    /**
     * 抽奖策略ID
     */
    private Long strategyId;

}
