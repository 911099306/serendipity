package com.monou.trigger.api;

import com.monou.trigger.api.dto.ActivityDrawRequestDTO;
import com.monou.trigger.api.dto.ActivityDrawResponseDTO;
import com.monou.types.model.Response;

/**
 * @author Serendipity
 * @description 抽奖活动服务
 * @date 2024-07-22 00:02
 **/
public interface IRaffleActivityService {

    /**
     * 活动装配，数据预热缓存
     * @param activityId 活动ID
     * @return 装配结果
     */
    Response<Boolean> armory(Long activityId);

    /**
     * 活动抽奖接口
     * @param request 请求对象
     * @return 返回结果
     */
    Response<ActivityDrawResponseDTO> draw(ActivityDrawRequestDTO request);
}
