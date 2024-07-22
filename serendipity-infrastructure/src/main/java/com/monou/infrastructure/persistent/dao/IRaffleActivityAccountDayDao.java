package com.monou.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import com.monou.infrastructure.persistent.po.RaffleActivityAccountDay;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Serendipity
 * @description 抽奖活动账户表-日次数
 * @date 2024-07-17 23:10
 **/
@Mapper
public interface IRaffleActivityAccountDayDao {

    /**
     * 查询日额度信息
     * @param raffleActivityAccountDayReq 用户id 活动id 日
     * @return 日额度信息
     */
    @DBRouter
    RaffleActivityAccountDay queryActivityAccountDayByUserId(RaffleActivityAccountDay raffleActivityAccountDayReq);

    /**
     * 更新镜像 日额度
     * @param raffleActivityAccountDay 日额度记录
     * @return 修改成功
     */
    int updateActivityAccountDaySubtractionQuota(RaffleActivityAccountDay raffleActivityAccountDay);

    /**
     * 插入镜像 日额度信息
     * @param raffleActivityAccountDay 日额度记录
     */
    void insertActivityAccountDay(RaffleActivityAccountDay raffleActivityAccountDay);

    /**
     * 查询当天查询次数
     * @param raffleActivityAccountDay 当前信息
     * @return 查询次数
     */
    @DBRouter
    Integer queryRaffleActivityAccountDayPartakeCount(RaffleActivityAccountDay raffleActivityAccountDay);

}
