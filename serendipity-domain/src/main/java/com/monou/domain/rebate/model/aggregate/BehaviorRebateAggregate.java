package com.monou.domain.rebate.model.aggregate;

import com.monou.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import com.monou.domain.rebate.model.entity.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Serendipity
 * @description 行为返利聚合对象
 * @date 2024-07-24 22:13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BehaviorRebateAggregate {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 行为返利订单实体对象
     */
    private BehaviorRebateOrderEntity behaviorRebateOrderEntity;
    /**
     * 任务实体对象
     */
    private TaskEntity taskEntity;
}
