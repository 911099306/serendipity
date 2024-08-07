package com.monou.trigger.api.dto;

import lombok.Data;

/**
 * @author Serendipity
 * @description 用户活动账户请求对象
 * @date 2024-07-25 00:19
 */
@Data
public class UserActivityAccountRequestDTO {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;

}
