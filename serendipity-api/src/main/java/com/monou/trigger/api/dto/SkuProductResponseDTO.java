package com.monou.trigger.api.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Serendipity
 * @description sku商品对象
 * @date 2024-07-27 21:45
 **/
@Data
@Builder
public class SkuProductResponseDTO implements Serializable {

    /**
     * 商品sku
     */
    private Long sku;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 活动个人参与次数ID
     */
    private Long activityCountId;
    /**
     * 库存总量
     */
    private Integer stockCount;
    /**
     * 剩余库存
     */
    private Integer stockCountSurplus;
    /**
     * 商品金额【积分】
     */
    private BigDecimal productAmount;

    /**
     * 活动商品数量
     */
    private ActivityCount activityCount;

    @Data
    @Builder
    public static class ActivityCount {
        /**
         * 总次数
         */
        private Integer totalCount;

        /**
         * 日次数
         */
        private Integer dayCount;

        /**
         * 月次数
         */
        private Integer monthCount;

        public static ActivityCount buildActivityCount(int totalCount, int dayCount, int monthCount) {
            return ActivityCount.builder()
                    .totalCount(totalCount)
                    .dayCount(dayCount)
                    .monthCount(monthCount)
                    .build();
        }
    }

    public static SkuProductResponseDTO buildSkuProductResponseDTO(Long sku, Long activityId, Long activityCountId, Integer stockCount, Integer stockCountSurplus, BigDecimal productAmount, ActivityCount activityCount) {

        return SkuProductResponseDTO.builder()
                .sku(sku)
                .activityId(activityId)
                .activityCount(activityCount)
                .stockCount(stockCount)
                .activityCountId(activityCountId)
                .stockCountSurplus(stockCountSurplus)
                .productAmount(productAmount)
                .build();
    }
}