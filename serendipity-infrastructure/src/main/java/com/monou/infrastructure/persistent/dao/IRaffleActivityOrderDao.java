package com.monou.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.monou.infrastructure.persistent.po.RaffleActivityOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Serendipity
 * @description 抽奖活动单Dao
 * @date 2024-07-09 10:08
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IRaffleActivityOrderDao {

    /**
     * 插入活动数据
     *
     * @param raffleActivityOrder 抽奖活动实体
     */
    void insert(RaffleActivityOrder raffleActivityOrder);

    /**
     * 根据userId查询活动信息
     *
     * @param userId 用户id
     * @return 活动信息列表
     */
    @DBRouter
    List<RaffleActivityOrder> queryRaffleActivityOrderByUserId(String userId);

}
