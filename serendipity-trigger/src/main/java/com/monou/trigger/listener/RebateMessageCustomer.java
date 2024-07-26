package com.monou.trigger.listener;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.monou.domain.activity.model.entity.SkuRechargeEntity;
import com.monou.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.monou.domain.credit.model.entity.TradeEntity;
import com.monou.domain.credit.model.valobj.TradeNameVO;
import com.monou.domain.credit.model.valobj.TradeTypeVO;
import com.monou.domain.credit.service.ICreditAdjustService;
import com.monou.domain.rebate.event.SendRebateMessageEvent;
import com.monou.domain.rebate.model.valobj.RebateTypeVO;
import com.monou.types.enums.ResponseCode;
import com.monou.types.event.BaseEvent;
import com.monou.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author Serendipity
 * @description 监听；行为返利消息
 * @date 2024-07-24 23:46
 */
@Slf4j
@Component
public class RebateMessageCustomer {

    @Value("${spring.rabbitmq.topic.send_rebate}")
    private String topic;
    @Resource
    private IRaffleActivityAccountQuotaService raffleActivityAccountQuotaService;
    @Resource
    private ICreditAdjustService creditAdjustService;

    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.send_rebate}"))
    public void listener(String message) {
        try {
            log.info("监听用户行为返利消息 topic: {} message: {}", topic, message);
            // 1. 转换消息
            BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage> eventMessage = JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage>>() {}.getType());
            SendRebateMessageEvent.RebateMessage rebateMessage = eventMessage.getData();

            // 2. 入账奖励
            switch (rebateMessage.getRebateType()) {
                case "sku":
                    SkuRechargeEntity skuRechargeEntity = buildSkuRechargeEntity(rebateMessage);
                    raffleActivityAccountQuotaService.createOrder(skuRechargeEntity);
                    break;
                case "integral":
                    TradeEntity tradeEntity = buildTradeEntity(rebateMessage);
                    creditAdjustService.createOrder(tradeEntity);
                    break;
                default:
                    break;
            }

        } catch (AppException e) {
            if (ResponseCode.INDEX_DUP.getCode().equals(e.getCode())) {
                log.warn("监听用户行为返利消息，消费重复 topic: {} message: {}", topic, message, e);
                return;
            }
            throw e;
        } catch (Exception e) {
            log.error("监听用户行为返利消息，消费失败 topic: {} message: {}", topic, message, e);
            throw e;
        }
    }

    private  TradeEntity buildTradeEntity(SendRebateMessageEvent.RebateMessage rebateMessage) {
        TradeEntity tradeEntity = new TradeEntity();
        tradeEntity.setUserId(rebateMessage.getUserId());
        tradeEntity.setTradeName(TradeNameVO.REBATE);
        tradeEntity.setTradeType(TradeTypeVO.FORWARD);
        tradeEntity.setAmount(new BigDecimal(rebateMessage.getRebateConfig()));
        tradeEntity.setOutBusinessNo(rebateMessage.getBizId());
        return tradeEntity;
    }

    private  SkuRechargeEntity buildSkuRechargeEntity(SendRebateMessageEvent.RebateMessage rebateMessage) {
        SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
        skuRechargeEntity.setUserId(rebateMessage.getUserId());
        skuRechargeEntity.setSku(Long.valueOf(rebateMessage.getRebateConfig()));
        skuRechargeEntity.setOutBusinessNo(rebateMessage.getBizId());
        return skuRechargeEntity;
    }

}
