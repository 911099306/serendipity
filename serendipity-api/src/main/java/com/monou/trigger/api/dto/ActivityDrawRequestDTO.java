package com.monou.trigger.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Serendipity
 * @description 活动抽奖请求对象
 * @date 2024-07-22 00:00
 **/
@Data
public class ActivityDrawRequestDTO implements Serializable {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;


}
