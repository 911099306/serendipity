package com.monou.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Serendipity
 * @description 抽奖活动账户表 持久化对象
 * @date 2024-07-14 22:23
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleActivityAccount {

    /**
     * 自增ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 总次数
     */
    private Integer totalCount;

    /**
     * 总次数-剩余
     */
    private Integer totalCountSurplus;

    /**
     * 日次数
     */
    private Integer dayCount;

    /**
     * 日次数-剩余
     */
    private Integer dayCountSurplus;

    /**
     * 月次数
     */
    private Integer monthCount;

    /**
     * 月次数-剩余
     */
    private Integer monthCountSurplus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;



    public static RaffleActivityAccount buildRaffleActivityAccountTotal(String userId, Long activityId, Integer totalCount, Integer totalCountSurplus, Integer dayCount, Integer dayCountSurplus, Integer mouCount, Integer monthCountSurplus) {

        RaffleActivityAccount raffleActivityAccount = new RaffleActivityAccount();
        raffleActivityAccount.setUserId(userId);
        raffleActivityAccount.setActivityId(activityId);
        raffleActivityAccount.setTotalCount(totalCount);
        raffleActivityAccount.setTotalCountSurplus(totalCountSurplus);
        raffleActivityAccount.setDayCount(dayCount);
        raffleActivityAccount.setDayCountSurplus(dayCountSurplus);
        raffleActivityAccount.setMonthCount(mouCount);
        raffleActivityAccount.setMonthCountSurplus(monthCountSurplus);
        return raffleActivityAccount;
    }

}
