package com.monou.domain.rebate.repository;

import com.monou.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import com.monou.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import com.monou.domain.rebate.model.valobj.BehaviorTypeVO;
import com.monou.domain.rebate.model.valobj.DailyBehaviorRebateVO;

import java.util.List;

/**
 * @author Serendipity
 * @description 行为返利服务仓储接口
 * @date 2024-07-24 22:16
 **/
public interface IBehaviorRebateRepository {

    /**
     * 查询改行为类型的配置对象
     *
     * @param behaviorTypeVO 行为类型枚举值类型
     * @return 日常行为返利配置值对象集合
     */
    List<DailyBehaviorRebateVO> queryDailyBehaviorRebateConfig(BehaviorTypeVO behaviorTypeVO);

    /**
     * 保存用户返利订单，及发送MQ消息和task任务表信息
     *
     * @param userId                   用户id
     * @param behaviorRebateAggregates 返利流对象
     */
    void saveUserRebateRecord(String userId, List<BehaviorRebateAggregate> behaviorRebateAggregates);

    /**
     * 根据用户 和 外部单号，查询返利订单
     *
     * @param userId 用户id
     * @param outBusinessNo 外部单号
     * @return 返利订单列表
     */
    List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo);


}
