package com.monou.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Serendipity
 * @description 抽奖奖品列表中每一个奖品得属性，响应对象
 * @date 2024-07-02 23:41
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardListResponse {

    /**
     * 奖品ID
     */
    private Integer awardId;
    /**
     * 奖品标题
     */
    private String awardTitle;
    /**
     * 奖品副标题【抽奖1次后解锁】
     */
    private String awardSubtitle;
    /**
     * 排序编号
     */
    private Integer sort;
}
