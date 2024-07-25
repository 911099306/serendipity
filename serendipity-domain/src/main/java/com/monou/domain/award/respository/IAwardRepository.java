package com.monou.domain.award.respository;

import com.monou.domain.award.model.aggregate.GiveOutPrizesAggregate;
import com.monou.domain.award.model.aggregate.UserAwardRecordAggregate;

/**
 * @author Serendipity
 * @description 奖品仓储服务
 * @date 2024-07-21 23:03
 **/
public interface IAwardRepository {

    /**
     * 保存用户抽奖记录 [消息对象、任务对象]
     *
     * @param userAwardRecordAggregate 用户抽奖记录
     */
    void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate);

    String queryAwardConfig(Integer awardId);

    void saveGiveOutPrizesAggregate(GiveOutPrizesAggregate giveOutPrizesAggregate);

    String queryAwardKey(Integer awardId);

}
