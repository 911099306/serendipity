package com.monou.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Serendipity
 * @description 抽奖活动账户表-月次数
 * @date 2024-07-17 23:12
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleActivityAccountMonth {

    private static final SimpleDateFormat dateFormatMonth = new SimpleDateFormat("yyyy-MM");


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
     * 月（yyyy-mm）
     */
    private String month;
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

    public static String currentMonth() {
        return dateFormatMonth.format(new Date());
    }

    public static RaffleActivityAccountMonth buildRaffleActivityAccountMouth(String userId, Long activityId, String currentMonth, Integer monthCount, Integer monthCountSurplus) {
        RaffleActivityAccountMonth raffleActivityAccountMonth = new RaffleActivityAccountMonth();
        raffleActivityAccountMonth.setUserId(userId);
        raffleActivityAccountMonth.setActivityId(activityId);
        raffleActivityAccountMonth.setMonth(currentMonth);
        raffleActivityAccountMonth.setMonthCount(monthCount);
        raffleActivityAccountMonth.setMonthCountSurplus(monthCountSurplus);
        return raffleActivityAccountMonth;
    }


}
