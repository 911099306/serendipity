package com.monou.infrastructure.persistent.dao;

import com.monou.infrastructure.persistent.po.RaffleActivitySku;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Serendipity
 * @description 商品sku dao
 * @date 2024-07-15 00:37
 **/
@Mapper
public interface IRaffleActivitySkuDao {

    /**
     * 查询活动SKU信息
     * @param sku skuId
     * @return sku信息
     */
    RaffleActivitySku queryActivitySku(Long sku);
}
