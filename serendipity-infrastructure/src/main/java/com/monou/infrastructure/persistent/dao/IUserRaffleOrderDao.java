package com.monou.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.monou.infrastructure.persistent.po.UserRaffleOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Serendipity
 * @description 用户抽奖订单表
 * @date 2024-07-17 23:11
 **/
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserRaffleOrderDao {

    /**
     * 插入订单
     * @param userRaffleOrder 订单信息
     */
    void insert(UserRaffleOrder userRaffleOrder);

    /**
     * 查询未使用的订单
     *
     * @param userRaffleOrderReq 参与抽奖活动实体对象
     * @return 未使用的订单
     */
    @DBRouter
    UserRaffleOrder queryNoUsedRaffleOrder(UserRaffleOrder userRaffleOrderReq);

    /**
     * 更新抽奖单为已使用
     * @param userRaffleOrderReq 抽奖单
     * @return 修改行数
     */
    int updateUserRaffleOrderStateUsed(UserRaffleOrder userRaffleOrderReq);



}
