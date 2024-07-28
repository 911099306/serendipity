package com.monou.domain.award.service.distribute.impl;

import com.monou.domain.award.model.entity.DistributeAwardEntity;
import com.monou.domain.award.service.distribute.IDistributeAward;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Serendipity
 * @description
 * @date 2024-07-28 01:09
 **/
@Slf4j
@Component("openai_model")
public class OpenaiModelAward implements IDistributeAward {
    @Override
    public void giveOutPrizes(DistributeAwardEntity distributeAwardEntity) {
        log.warn("openai_model 暂未接入...");
    }
}
