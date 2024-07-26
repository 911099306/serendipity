package com.monou.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import com.monou.infrastructure.persistent.po.RaffleActivityAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Serendipity
 * @description 抽奖活动账户表
 * @date 2024-07-09 10:05
 */
@Mapper
public interface IRaffleActivityAccountDao {

    /**
     * 插入用户账户次数信息
     *
     * @param raffleActivityAccount 订单信息
     */
    void insert(RaffleActivityAccount raffleActivityAccount);

    /**
     * 定额更新用户账户次数
     *
     * @param raffleActivityAccount 用户账户信息
     * @return 更新次数
     */
    int updateAccountQuota(RaffleActivityAccount raffleActivityAccount);

    /**
     * 查询账户额度信息
     * @param raffleActivityAccountReq 实体信息
     * @return 账户信息
     */
    @DBRouter
    RaffleActivityAccount queryActivityAccountByUserId(RaffleActivityAccount raffleActivityAccountReq);

    /**
     * 更新总账户总额度信息, 抽奖一次,额度减一
     * @param raffleActivityAccount 账户信息实体
     * @return 影响行数
     */
    int updateActivityAccountSubtractionQuota(RaffleActivityAccount raffleActivityAccount);

    /**
     * 更改活动 月 库存数量 , 减一, 并更改修改时间
     * @param raffleActivityAccount 月活动记录
     */
    void updateActivityAccountMonthSurplusImageQuota(RaffleActivityAccount raffleActivityAccount);

    /**
     * 更改活动 日 库存数量 , 减一, 并更改修改时间
     * @param raffleActivityAccount 日活动记录
     */
    void updateActivityAccountDaySurplusImageQuota(RaffleActivityAccount raffleActivityAccount);

    RaffleActivityAccount queryAccountByUserId(RaffleActivityAccount raffleActivityAccount);


}
