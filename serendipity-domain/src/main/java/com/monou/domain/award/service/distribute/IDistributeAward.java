package com.monou.domain.award.service.distribute;

import com.monou.domain.award.model.entity.DistributeAwardEntity;

/**
 * @author Serendipity
 * @description 分发奖品接口
 * @date 2024-07-26 00:04
 */
public interface IDistributeAward {

    void giveOutPrizes(DistributeAwardEntity distributeAwardEntity);

}