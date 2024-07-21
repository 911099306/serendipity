package com.monou.domain.task.model.entity;

import lombok.Data;

/**
 * @author Serendipity
 * @description 任务实体对象
 * @date 2024-07-21 23:06
 **/
@Data
public class TaskEntity {

    /**
     * 活动ID
     */
    private String userId;
    /**
     * 消息主题
     */
    private String topic;
    /**
     * 消息编号
     */
    private String messageId;
    /**
     * 消息主体
     */
    private String message;

}
