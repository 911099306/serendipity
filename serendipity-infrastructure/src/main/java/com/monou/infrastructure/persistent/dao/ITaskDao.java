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
     * 更新task记录状态
     * @param task task状态
     */
    @DBRouter
    void updateTaskSendMessageCompleted(Task task);

    @DBRouter
    void updateTaskSendMessageFail(Task task);

    List<Task> queryNoSendMessageTaskList();

}
