package com.monou.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.monou.infrastructure.persistent.po.UserBehaviorRebateOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Serendipity
 * @description 用户行为返利流水订单表
 * @date 2024-07-24 22:21
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserBehaviorRebateOrderDao {

    /**
     * 插入返利订单对象
     * @param userBehaviorRebateOrder 用户返利记录
     */
    void insert(UserBehaviorRebateOrder userBehaviorRebateOrder);

    /**
     * 查询返利记录
     * @param userBehaviorRebateOrderReq 返利请求
     * @return 返利记录
     */
    @DBRouter
    List<UserBehaviorRebateOrder> queryOrderByOutBusinessNo(UserBehaviorRebateOrder userBehaviorRebateOrderReq);


}
