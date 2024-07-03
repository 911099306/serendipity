package com.monou.trigger.api;

import com.monou.trigger.api.dto.RaffleAwardListRequest;
import com.monou.trigger.api.dto.RaffleAwardListResponse;
import com.monou.trigger.api.dto.RaffleRequest;
import com.monou.trigger.api.dto.RaffleResponse;
import com.monou.types.model.Response;

import java.util.List;

/**
 * @author Serendipity
 * @description 抽奖服务接口
 * @date 2024-07-02 23:37
 **/
public interface IRaffleService {

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
    Response<List<RaffleAwardListResponse>> queryRaffleAwardList(RaffleAwardListRequest request);


    /**
     * 随机抽奖接口
     *
     * @param request 随机抽奖接口请求参数
     * @return 抽奖奖品
     */
    Response<RaffleResponse> randomRaffle(RaffleRequest request);
}
