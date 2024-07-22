package com.monou.domain.activity.model.entity;

import com.monou.domain.activity.model.objval.UserRaffleOrderStateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Serendipity
 * @description 用户抽奖订单实体对象
 * @date 2024-07-19 21:44
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRaffleOrderEntity {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 策略ID
     */
    private Long strategyId;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 下单时间
     */
    private Date orderTime;
    /**
     * 活动状态；create-创建、used-已使用、cancel-已作废
     */
    private UserRaffleOrderStateVO orderState;
    /**
     * 结束时间
     */
    private Date endDateTime;


}
