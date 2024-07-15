package com.monou.domain.activity.service.rule.impl;

import com.monou.domain.activity.model.entity.ActivityCountEntity;
import com.monou.domain.activity.model.entity.ActivityEntity;
import com.monou.domain.activity.model.entity.ActivitySkuEntity;
import com.monou.domain.activity.service.rule.AbstractActionChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Serendipity
 * @description
 * @date 2024-07-15 23:11
 **/
@Slf4j
@Component("activity_sku_stock_action")
public class ActivitySkuStockActionChain extends AbstractActionChain {

    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {

        log.info("活动责任链-商品库存处理【检验、扣减】 校验开始...");

        return true;
    }
}
