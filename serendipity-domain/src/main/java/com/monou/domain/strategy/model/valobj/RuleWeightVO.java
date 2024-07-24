package com.monou.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Serendipity
 * @description 权重规则值对象
 * @date 2024-07-25 00:26
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleWeightVO {

    /**
     * 原始规则值配置
     */
    private String ruleValue;
    /**
     * 权重值
     */
    private Integer weight;
    /**
     * 奖品配置
     */
    private List<Integer> awardIds;
    /**
     * 奖品列表
     */
    private List<Award> awardList;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Award {
        private Integer awardId;
        private String awardTitle;
    }

}
