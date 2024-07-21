package com.monou.trigger.api;

import com.monou.trigger.api.dto.RaffleAwardListRequestDTO;
import com.monou.trigger.api.dto.RaffleAwardListResponseDTO;
import com.monou.trigger.api.dto.RaffleStrategyRequestDTO;
import com.monou.trigger.api.dto.RaffleStrategyResponseDTO;
import com.monou.types.model.Response;

import java.util.List;

/**
 * @author Serendipity
 * @description 抽奖服务接口
 * @date 2024-07-02 23:37
 **/
public interface IRaffleStrategyService {

    /**
     * 策略装配接口
     *
     * @param strategyId 策略Id
     * @return 装配结果
     */
    Response<Boolean> strategyArmory(Long strategyId);


    /**
     * 查询抽奖奖品列表
     *
     * @param request 抽奖奖品列表查询请求参数
     * @return 奖品列表
     */
    Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(RaffleAwardListRequestDTO request);


    /**
     * 随机抽奖接口
     *
     * @param request 随机抽奖接口请求参数
     * @return 抽奖奖品
     */
    Response<RaffleStrategyResponseDTO> randomRaffle(RaffleStrategyRequestDTO request);
}
