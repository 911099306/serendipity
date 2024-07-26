package com.monou.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.monou.infrastructure.persistent.po.UserCreditOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Serendipity
 * @description 用户积分流水单 DAO
 * @date 2024-07-27 00:43
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserCreditOrderDao {

    /**
     * 插入用户积分流水
     *
     * @param userCreditOrderReq 用户积分流水
     */
    void insert(UserCreditOrder userCreditOrderReq);

}
