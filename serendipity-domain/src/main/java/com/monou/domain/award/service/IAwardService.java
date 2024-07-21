package com.monou.domain.award.service;

import com.monou.domain.award.model.entity.UserAwardRecordEntity;

/**
 * @author Serendipity
 * @description 奖品服务接口
 * @date 2024-07-21 23:05
 **/
public interface IAwardService {

    /**
     * 保存用户抽奖记录
     * @param userAwardRecordEntity 用户抽奖实体记录
     */
    void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity);

}
