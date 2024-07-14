package com.monou.infrastructure.persistent.dao;

import com.monou.infrastructure.persistent.po.RaffleActivityCount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Serendipity
 * @description 抽奖活动次数配置表Dao
 * @date 2024-07-09 10:07
 */
@Mapper
public interface IRaffleActivityCountDao {

    /**
     * 通过活动参与次数id查询活动数量
     * @param activityCountId 活动参与次数id
     * @return 活动参与次数
     */
    RaffleActivityCount queryRaffleActivityCountByActivityCountId(Long activityCountId);


}
