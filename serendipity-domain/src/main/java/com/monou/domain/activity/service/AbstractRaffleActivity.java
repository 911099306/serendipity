package com.monou.domain.activity.service;

import com.alibaba.fastjson.JSON;
import com.monou.domain.activity.model.aggregate.CreateOrderAggregate;
import com.monou.domain.activity.model.entity.*;
import com.monou.domain.activity.respository.IActivityRepository;
import com.monou.domain.activity.service.rule.IActionChain;
import com.monou.domain.activity.service.rule.factory.DefaultActivityChainFactory;
import com.monou.types.enums.ResponseCode;
import com.monou.types.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Serendipity
 * @description 抽奖活动抽象类，定义标准的流程
 * @date 2024-07-15 00:51
 **/
@Slf4j
public abstract class AbstractRaffleActivity extends RaffleActivitySupport implements IRaffleOrder {

    public AbstractRaffleActivity(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory) {
        super(activityRepository, defaultActivityChainFactory);
    }

    @Override
    public ActivityOrderEntity createRaffleActivityOrder(ActivityShopCartEntity activityShopCartEntity) {
        // 1. 通过sku查询活动信息
        ActivitySkuEntity activitySkuEntity = activityRepository.queryActivitySku(activityShopCartEntity.getSku());
        // 2. 查询活动信息
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        // 3. 查询次数信息（用户在活动上可参与的次数）
        ActivityCountEntity activityCountEntity = activityRepository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        log.info("查询结果：{} {} {}", JSON.toJSONString(activitySkuEntity), JSON.toJSONString(activityEntity), JSON.toJSONString(activityCountEntity));

        return ActivityOrderEntity.builder().build();
    }

    @Override
    public String createSkuRechargeOrder(SkuRechargeEntity skuRechargeEntity) {

        // 1. 参数校验
        String userId = skuRechargeEntity.getUserId();
        Long sku = skuRechargeEntity.getSku();
        String outBusinessNo = skuRechargeEntity.getOutBusinessNo();
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(outBusinessNo) || sku == null) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2. 查询基础信息
        // 2.1 通过 sku 查询活动信息
        ActivitySkuEntity activitySkuEntity = this.queryActivitySku(sku);
        // 2.2 查询活动信息
        ActivityEntity activityEntity = this.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        // 2.3 查询次数信息 (用户在该活动上可参与的次数)
        ActivityCountEntity activityCountEntity = this.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        // 3. 活动动作规则校验
        IActionChain actionChain = defaultActivityChainFactory.openActionChain();
        actionChain.action(activitySkuEntity, activityEntity, activityCountEntity);

        // 4. 构建订单聚合对象
        CreateOrderAggregate createOrderAggregate = buildOrderAggregate(skuRechargeEntity, activitySkuEntity, activityEntity, activityCountEntity);

        // 5. 保存订单
        doSaveOrder(createOrderAggregate);

        // 6. 返回单号
        return createOrderAggregate.getActivityOrderEntity().getOrderId();
    }


    /**
     * 构建订单聚合对象
     * @param skuRechargeEntity
     * @param activitySkuEntity
     * @param activityEntity
     * @param activityCountEntity
     * @return
     */
    protected abstract CreateOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) ;


    /**
     * 保存订单
     *
     * @param createOrderAggregate 聚合对象
     */
    protected abstract void doSaveOrder(CreateOrderAggregate createOrderAggregate);
}
