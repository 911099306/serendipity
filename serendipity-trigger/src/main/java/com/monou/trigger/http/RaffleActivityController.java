package com.monou.trigger.http;

import com.monou.domain.activity.model.entity.UserRaffleOrderEntity;
import com.monou.domain.activity.service.IRaffleActivityPartakeService;
import com.monou.domain.activity.service.armory.IActivityArmory;
import com.monou.domain.award.model.entity.UserAwardRecordEntity;
import com.monou.domain.award.model.valobj.AwardStateVO;
import com.monou.domain.award.service.IAwardService;
import com.monou.domain.strategy.model.entity.RaffleAwardEntity;
import com.monou.domain.strategy.model.entity.RaffleFactorEntity;
import com.monou.domain.strategy.service.IRaffleStrategy;
import com.monou.domain.strategy.service.armory.IStrategyArmory;
import com.monou.trigger.api.IRaffleActivityService;
import com.monou.trigger.api.dto.ActivityDrawRequestDTO;
import com.monou.trigger.api.dto.ActivityDrawResponseDTO;
import com.monou.types.enums.ResponseCode;
import com.monou.types.exception.AppException;
import com.monou.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Serendipity
 * @description 抽奖活动服务
 * 注意；在不引用 application/case 层的时候，就需要让接口实现层来做领域的串联。
 * 一些较大规模的系统，需要加入 case 层。
 * @date 2024-07-22 00:11
 **/
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/raffle/activity/")
public class RaffleActivityController implements IRaffleActivityService {
    @Resource
    private IRaffleActivityPartakeService raffleActivityPartakeService;
    @Resource
    private IRaffleStrategy raffleStrategy;
    @Resource
    private IAwardService awardService;
    @Resource
    private IActivityArmory activityArmory;
    @Resource
    private IStrategyArmory strategyArmory;

    /**
     * 活动装配 - 数据预热 | 把活动配置的对应的 sku 一起装配
     *
     * @param activityId 活动ID
     * @return 装配结果
     * <p>
     * 接口：<a href="http://localhost:8091/api/v1/raffle/activity/armory">/api/v1/raffle/activity/armory</a>
     * 入参：{"activityId":100001,"userId":"xiaofuge"}
     * <p>
     * curl --request GET \
     * --url 'http://localhost:8091/api/v1/raffle/activity/armory?activityId=100301'
     */
    @Override
    @RequestMapping(value = "armory", method = RequestMethod.GET)
    public Response<Boolean> armory(@RequestParam Long activityId) {
        try {
            log.info("活动装配，数据预热，开始 activityId:{}", activityId);

            // 1. 活动装配
            activityArmory.assembleActivitySkuByActivityId(activityId);
            // 2. 策略装配
            strategyArmory.assembleLotteryStrategyByActivityId(activityId);
            Response<Boolean> response = Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(true)
                    .build();

            log.info("活动装配，数据预热，完成 activityId:{}", activityId);
            return response;
        } catch (Exception e) {
            log.error("活动装配，数据预热，失败 activityId:{}", activityId, e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 抽奖接口
     *
     * @param request 请求对象
     * @return 抽奖结果
     * <p>
     * 接口：<a href="http://localhost:8091/api/v1/raffle/activity/draw">/api/v1/raffle/activity/draw</a>
     * 入参：{"activityId":100001,"userId":"xiaofuge"}
     * <p>
     * curl --request POST \
     * --url http://localhost:8091/api/v1/raffle/activity/draw \
     * --header 'content-type: application/json' \
     * --data '{
     * "userId":"xiaofuge",
     * "activityId": 100301
     * }'
     */
    @Override
    @RequestMapping(value = "draw", method = RequestMethod.POST)
    public Response<ActivityDrawResponseDTO> draw(@RequestBody ActivityDrawRequestDTO request) {
        try {
            log.info("活动抽奖 userId:{} activityId:{}", request.getUserId(), request.getActivityId());
            // 1. 参数校验
            if (StringUtils.isBlank(request.getUserId()) || request.getActivityId() == null) {
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
            }
            // 2. 参与活动 - 创建参与记录订单
            UserRaffleOrderEntity orderEntity = raffleActivityPartakeService.createOrder(request.getUserId(), request.getActivityId());
            log.info("活动抽奖，创建订单 userId:{} activityId:{} orderId:{}", request.getUserId(), request.getActivityId(), orderEntity.getOrderId());

            // 3. 抽奖策略 - 执行抽奖
            RaffleFactorEntity raffleFactor = RaffleFactorEntity.builder()
                    .userId(orderEntity.getUserId())
                    .strategyId(orderEntity.getStrategyId())
                    .endDateTime(orderEntity.getEndDateTime())
                    .build();
            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactor);

            // 4. 存放结果 - 写入中奖记录
            UserAwardRecordEntity userAwardRecord = buildUserAwardRecordEntity(orderEntity, raffleAwardEntity);
            awardService.saveUserAwardRecord(userAwardRecord);

            // 5. 返回结果
            ActivityDrawResponseDTO activityDrawResponseDTO = ActivityDrawResponseDTO.builder()
                    .awardId(raffleAwardEntity.getAwardId())
                    .awardTitle(raffleAwardEntity.getAwardTitle())
                    .awardIndex(raffleAwardEntity.getSort())
                    .build();
            return Response.<ActivityDrawResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(activityDrawResponseDTO)
                    .build();

        } catch (AppException e) {
            log.error("活动抽奖失败 userId:{} activityId:{}", request.getUserId(), request.getActivityId(), e);
            return Response.<ActivityDrawResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("活动抽奖失败 userId:{} activityId:{}", request.getUserId(), request.getActivityId(), e);
            return Response.<ActivityDrawResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    private static UserAwardRecordEntity buildUserAwardRecordEntity(UserRaffleOrderEntity orderEntity, RaffleAwardEntity raffleAwardEntity) {
        return UserAwardRecordEntity.builder()
                .userId(orderEntity.getUserId())
                .activityId(orderEntity.getActivityId())
                .strategyId(orderEntity.getStrategyId())
                .orderId(orderEntity.getOrderId())
                .awardId(raffleAwardEntity.getAwardId())
                .awardTitle(raffleAwardEntity.getAwardTitle())
                .awardTime(new Date())
                .awardState(AwardStateVO.create)
                .build();
    }
}
