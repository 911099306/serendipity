package com.monou.domain.credit.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Serendipity
 * @description 积分账户实体
 * @date 2024-07-27 00:40
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditAccountEntity {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 可用积分，每次扣减的值
     */
    private BigDecimal adjustAmount;

}
