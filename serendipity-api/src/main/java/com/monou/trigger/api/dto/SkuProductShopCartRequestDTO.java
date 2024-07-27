package com.monou.trigger.api.dto;

import lombok.Data;

/**
 * @author Serendipity
 * @description 商品购物车请求对象
 * @date 2024-07-27 21:45
 **/
@Data
public class SkuProductShopCartRequestDTO {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * sku 商品
     */
    private Long sku;

}

