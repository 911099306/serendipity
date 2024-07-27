package com.monou.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Serendipity
 * @description 抽奖列表请求对象
 * @date 2024-07-02 23:40
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleAwardListRequestDTO implements Serializable {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 抽奖活动ID
     */
    private Long activityId;

}
