package com.monou.domain.activity.service.quota;

import com.alibaba.fastjson.JSON;
import com.monou.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.monou.domain.activity.model.entity.*;
import com.monou.domain.activity.respository.IActivityRepository;
import com.monou.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.monou.domain.activity.service.quota.policy.ITradePolicy;
import com.monou.domain.activity.service.quota.rule.IActionChain;
import com.monou.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;
import com.monou.types.enums.ResponseCode;
import com.monou.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author Serendipity
 * @description 抽奖活动抽象类，定义标准的流程
 * @date 2024-07-15 00:51
 **/
@Slf4j
public abstract class AbstractRaffleActivityAccountQuota extends RaffleActivityAccountQuotaSupport implements IRaffleActivityAccountQuotaService {

    /**
     * 不同类型的交易策略实现类，通过构造函数注入到 Map 中，教程；https://bugstack.cn/md/road-map/spring-dependency-injection.html
     */
    private final Map<String, ITradePolicy> tradePolicyGroup;

    public AbstractRaffleActivityAccountQuota(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory, Map<String, ITradePolicy> tradePolicyGroup) {
        super(activityRepository, defaultActivityChainFactory);
        this.tradePolicyGroup = tradePolicyGroup;
    }

    @Override
    public String createOrder(SkuRechargeEntity skuRechargeEntity) {

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

        // 3. 活动动作规则校验 「过滤失败则直接抛异常」- 责任链扣减sku库存
        IActionChain actionChain = defaultActivityChainFactory.openActionChain();
        actionChain.action(activitySkuEntity, activityEntity, activityCountEntity);

        // 4. 构建订单聚合对象
        CreateQuotaOrderAggregate createOrderAggregate = buildOrderAggregate(skuRechargeEntity, activitySkuEntity, activityEntity, activityCountEntity);

        // 5. 交易策略 - 【积分兑换，支付类订单】【返利无支付交易订单，直接充值到账】【订单状态变更交易类型策略】
        ITradePolicy tradePolicy = tradePolicyGroup.get(skuRechargeEntity.getOrderTradeType().getCode());
        tradePolicy.trade(createOrderAggregate);

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
    protected abstract CreateQuotaOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) ;


}
