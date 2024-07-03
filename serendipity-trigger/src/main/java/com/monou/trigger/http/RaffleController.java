package com.monou.trigger.http;

import com.alibaba.fastjson.JSON;
import com.monou.domain.strategy.model.entity.RaffleAwardEntity;
import com.monou.domain.strategy.model.entity.RaffleFactorEntity;
import com.monou.domain.strategy.model.entity.StrategyAwardEntity;
import com.monou.domain.strategy.service.IRaffleAward;
import com.monou.domain.strategy.service.IRaffleStrategy;
import com.monou.domain.strategy.service.armory.IStrategyArmory;
import com.monou.trigger.api.IRaffleService;
import com.monou.trigger.api.dto.RaffleAwardListRequest;
import com.monou.trigger.api.dto.RaffleAwardListResponse;
import com.monou.trigger.api.dto.RaffleRequest;
import com.monou.trigger.api.dto.RaffleResponse;
import com.monou.types.enums.ResponseCode;
import com.monou.types.exception.AppException;
import com.monou.types.model.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Serendipity
 * @description 营销抽奖服务http
 * @date 2024-07-02 23:50
 **/
@Slf4j
@RestController()
@RequiredArgsConstructor
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/raffle/")
public class RaffleController implements IRaffleService {

    private final IStrategyArmory strategyArmory;
    private final IRaffleAward raffleAward;
    private final IRaffleStrategy raffleStrategy;

    /**
     * 策略装配，将策略信息装配到缓存中: /api/v1/raffle/strategy_armory
     * <a href="http://localhost:8091/api/v1/raffle/strategy_armory"/>
     *
     * @param strategyId 策略Id
     * @return
     */
    @Override
    @RequestMapping(value = "strategy_armory", method = RequestMethod.GET)
    public Response<Boolean> strategyArmory(@RequestParam Long strategyId) {

        try {
            log.info("RaffleController.strategyArmory 抽奖策略装配开始... strategyId:{} ", strategyId);

            Boolean assembleStatus = strategyArmory.assembleLotteryStrategy(strategyId);
            Response<Boolean> response = Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(assembleStatus)
                    .build();
            log.info("RaffleController.strategyArmory 抽奖策略装配完成... strategyId:{} ", strategyId);
            return response;
        } catch (Exception e) {
            log.info("RaffleController.strategyArmory 抽奖策略装配失败... strategyId:{}, error ", strategyId, e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 根据策略id 查村待抽奖奖品列表: /api/v1/raffle/query_raffle_award_list:
     * <a href="http://localhost:8091/api/v1/raffle/query_raffle_award_list"/>
     *
     * @param request 抽奖奖品列表查询请求参数 {"strategyId":1000001}
     * @return 抽奖奖品列表
     */
    @Override
    @RequestMapping(value = "query_raffle_award_list", method = RequestMethod.POST)
    public Response<List<RaffleAwardListResponse>> queryRaffleAwardList(@RequestBody RaffleAwardListRequest request) {
        try {
            log.info("RaffleController.queryRaffleAwardList 查询奖品列表... strategyId:{} ", request.getStrategyId());

            List<StrategyAwardEntity> awardEntityList = raffleAward.queryRaffleStrategyAwardList(request.getStrategyId());
            List<RaffleAwardListResponse> raffleAwardList = awardEntityList.stream().map(award -> RaffleAwardListResponse.builder()
                            .awardId(award.getAwardId())
                            .awardTitle(award.getAwardTitle())
                            .awardSubtitle(award.getAwardSubtitle())
                            .sort(award.getSort())
                            .build())
                    .collect(Collectors.toList());

            return Response.<List<RaffleAwardListResponse>>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(raffleAwardList)
                    .build();

        } catch (Exception e) {
            log.info("RaffleController.queryRaffleAwardList 查询奖品列表失败... strategyId:{} ", request.getStrategyId());
            return Response.<List<RaffleAwardListResponse>>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 随即抽奖: /api/v1/raffle/random_raffle
     * <a href="http://localhost:8091/api/v1/raffle/random_raffle"/>
     *
     * @param request 随机抽奖接口请求参数 {"strategyId":1000001}
     * @return 抽奖结果
     */
    @Override
    @RequestMapping(value = "random_raffle", method = RequestMethod.POST)
    public Response<RaffleResponse> randomRaffle(@RequestBody RaffleRequest request) {

        try {
            log.info("RaffleController.randomRaffle 随机抽奖开始 strategyId: {}", request.getStrategyId());
            // 调用抽奖接口
            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(RaffleFactorEntity.builder()
                    .userId("system")
                    .strategyId(request.getStrategyId())
                    .build());
            // 封装返回结果
            Response<RaffleResponse> response = Response.<RaffleResponse>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(RaffleResponse.builder()
                            .awardId(raffleAwardEntity.getAwardId())
                            .awardIndex(raffleAwardEntity.getSort())
                            .build())
                    .build();
            log.info("RaffleController.randomRaffle 随机抽奖完成 strategyId: {} response: {}", request.getStrategyId(), JSON.toJSONString(response));
            return response;
        } catch (AppException e) {
            log.error("RaffleController.randomRaffle 随机抽奖失败 strategyId：{} {}", request.getStrategyId(), e.getInfo());
            return Response.<RaffleResponse>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("RaffleController.randomRaffle 随机抽奖失败 strategyId：{}", request.getStrategyId(), e);
            return Response.<RaffleResponse>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
