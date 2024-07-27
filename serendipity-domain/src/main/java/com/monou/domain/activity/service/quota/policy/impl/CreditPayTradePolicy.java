package com.monou.domain.activity.service.quota.policy.impl;

import com.monou.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.monou.domain.activity.model.objval.OrderStateVO;
import com.monou.domain.activity.respository.IActivityRepository;
import com.monou.domain.activity.service.quota.policy.ITradePolicy;
import org.springframework.stereotype.Service;

/**
 * @author Serendipity
 * @description 积分兑换，支付类订单
 * @date 2024-07-27 17:29
 **/
@Service("credit_pay_trade")
public class CreditPayTradePolicy implements ITradePolicy {

    private final IActivityRepository activityRepository;

    public CreditPayTradePolicy(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate) {
        // 需要支付，待支付类型
        createQuotaOrderAggregate.setOrderState(OrderStateVO.wait_pay);
        activityRepository.doSaveCreditPayOrder(createQuotaOrderAggregate);
    }

}