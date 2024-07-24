package com.monou.infrastructure.persistent.dao;

import com.monou.infrastructure.persistent.po.DailyBehaviorRebate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Serendipity
 * @description 日常行为返利活动配置
 * @date 2024-07-24 22:20
 */
@Mapper
public interface IDailyBehaviorRebateDao {

    /**
     * 通过动作类型查询
     * @param behaviorType 返利类型
     * @return 配置类型
     */
    List<DailyBehaviorRebate> queryDailyBehaviorRebateByBehaviorType(String behaviorType);

}
