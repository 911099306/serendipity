package com.monou.domain.rebate.service;

import com.monou.domain.rebate.model.entity.BehaviorEntity;

import java.util.List;

/**
 * @author Serendipity
 * @description 行为返利服务接口
 * @date 2024-07-24 22:16
 **/
public interface IBehaviorRebateService {

    /**
     * 创建行为动作的入账订单
     *
     * @param behaviorEntity 行为实体对象
     * @return 订单ID
     */
    List<String> createOrder(BehaviorEntity behaviorEntity);

}
