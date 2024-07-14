package com.monou.infrastructure.persistent.dao;

import com.monou.infrastructure.persistent.po.RaffleActivity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Serendipity
 * @description 抽奖活动表Dao
 * @date 2024-07-09 10:04
 */
@Mapper
public interface IRaffleActivityDao {

    RaffleActivity queryRaffleActivityByActivityId(Long activityId);

}
