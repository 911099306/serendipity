package com.monou.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Serendipity
 * @description 抽奖响应结果
 * @date 2024-07-02 23:43
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleStrategyResponseDTO {

    /**
     * 奖品ID
     */
    private Integer awardId;
    /**
     *  排序编号【策略奖品配置的奖品顺序编号】
     */
    private Integer awardIndex;
}
