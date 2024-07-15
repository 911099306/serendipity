package com.monou.infrastructure.persistent.dao;

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
}
