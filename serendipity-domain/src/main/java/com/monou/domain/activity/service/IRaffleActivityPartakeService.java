package com.monou.domain.activity.service;

import com.monou.domain.activity.model.entity.PartakeRaffleActivityEntity;
import com.monou.domain.activity.model.entity.UserRaffleOrderEntity;

/**
 * @author Serendipity
 * @description 抽奖活动参与服务
 * @date 2024-07-19 22:07
 **/

public interface IRaffleActivityPartakeService {
    /**
     * 创建抽奖单: 用户参与抽奖活动，扣减活动账户库存，产生抽奖单。
     *           如存在未被使用的抽奖单则直接返回已存在的抽奖单。
     *
     * @param partakeRaffleActivityEntity 参与抽奖活动实体对象
     * @return 用户抽奖订单实体对象
     */
    UserRaffleOrderEntity createOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);

}
