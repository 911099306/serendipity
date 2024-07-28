package com.monou.domain.award.service.distribute.impl;

import com.monou.domain.award.model.entity.DistributeAwardEntity;
import com.monou.domain.award.service.distribute.IDistributeAward;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Serendipity
 * @description
 * @date 2024-07-28 01:08
 **/
@Slf4j
@Component("openai_use_count")
public class OpenaiUseCountAward implements IDistributeAward {
    @Override
    public void giveOutPrizes(DistributeAwardEntity distributeAwardEntity) {
        log.warn("openai_use_count 暂未接入...");
    }
}
