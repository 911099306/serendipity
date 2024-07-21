package com.monou.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.monou.infrastructure.persistent.po.UserAwardRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Serendipity
 * @description 用户中奖记录表
 * @date 2024-07-17 23:11
 **/
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserAwardRecordDao {

    /**
     * 写入用户抽奖记录
     * @param userAwardRecord 用户抽奖记录
     */
    void insert(UserAwardRecord userAwardRecord);

}
