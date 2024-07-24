package com.monou.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import com.monou.infrastructure.persistent.po.RaffleActivityAccountMonth;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Serendipity
 * @description 抽奖活动账户表-月次数
 * @date 2024-07-17 23:10
 **/
@Mapper
public interface IRaffleActivityAccountMonthDao {

    /**
     * 查询 月额度信息
     * @param raffleActivityAccountMonthReq 用户id 活动id 月份
     * @return 月额度信息
     */
    @DBRouter
    RaffleActivityAccountMonth queryActivityAccountMonthByUserId(RaffleActivityAccountMonth raffleActivityAccountMonthReq);

    /**
     * 减少镜像 月额度记录
     *
     * @param raffleActivityAccountMonth 月额度记录
     * @return 修改成功
     */
    int updateActivityAccountMonthSubtractionQuota(RaffleActivityAccountMonth raffleActivityAccountMonth);

    /**
     * 插入 月 额度记录
     * @param raffleActivityAccountMonth 月 额度记录
     */
    void insertActivityAccountMonth(RaffleActivityAccountMonth raffleActivityAccountMonth);

    /**
     *  更新账户 - 月
     * @param raffleActivityAccountMonth 记录信息
     */
    void addAccountQuota(RaffleActivityAccountMonth raffleActivityAccountMonth);
}
