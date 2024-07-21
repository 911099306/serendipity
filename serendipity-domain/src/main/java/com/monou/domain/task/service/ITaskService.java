package com.monou.domain.task.service;

import com.monou.domain.task.model.entity.TaskEntity;

import java.util.List;

/**
 * @author Serendipity
 * @description 消息任务服务接口
 * @date 2024-07-21 23:07
 **/
public interface ITaskService {
    /**
     * 查询发送MQ失败和超时1分钟未发送的MQ
     *
     * @return 未发送的任务消息列表10条
     */
    List<TaskEntity> queryNoSendMessageTaskList();

    /**
     * 发送任务消息
     * @param taskEntity 任务信息
     */
    void sendMessage(TaskEntity taskEntity);

    /**
     * 更新任务执行完毕
     * @param userId 用户id
     * @param messageId 消息id
     */
    void updateTaskSendMessageCompleted(String userId, String messageId);

    /**
     * 更新任务执行失败
     * @param userId 用户id
     * @param messageId 消息id
     */
    void updateTaskSendMessageFail(String userId, String messageId);
}
