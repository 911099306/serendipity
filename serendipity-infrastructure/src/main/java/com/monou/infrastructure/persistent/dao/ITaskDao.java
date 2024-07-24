package com.monou.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import com.monou.infrastructure.persistent.po.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Serendipity
 * @description 任务表，发送MQ
 * @date 2024-07-17 23:11
 **/
@Mapper
public interface ITaskDao {

    /**
     * 写入任务信息
     *
     * @param task 任务信息
     */
    void insert(Task task);

    /**
     * 更新task任务状态完成
     *
     * @param task task状态
     */
    @DBRouter
    void updateTaskSendMessageCompleted(Task task);

    /**
     * 更新任务状态失败
     *
     * @param task 任务
     */
    @DBRouter
    void updateTaskSendMessageFail(Task task);

    /**
     * 查询task表示未发送MQ消息的记录
     *
     * @return 所有未发送MQ消息的记录
     */
    List<Task> queryNoSendMessageTaskList();

}
