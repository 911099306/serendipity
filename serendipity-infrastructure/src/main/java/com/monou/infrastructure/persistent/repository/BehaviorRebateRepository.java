package com.monou.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import com.alibaba.fastjson.JSON;
import com.monou.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import com.monou.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import com.monou.domain.rebate.model.entity.TaskEntity;
import com.monou.domain.rebate.model.valobj.BehaviorTypeVO;
import com.monou.domain.rebate.model.valobj.DailyBehaviorRebateVO;
import com.monou.domain.rebate.repository.IBehaviorRebateRepository;
import com.monou.infrastructure.event.EventPublisher;
import com.monou.infrastructure.persistent.dao.IDailyBehaviorRebateDao;
import com.monou.infrastructure.persistent.dao.ITaskDao;
import com.monou.infrastructure.persistent.dao.IUserBehaviorRebateOrderDao;
import com.monou.infrastructure.persistent.po.DailyBehaviorRebate;
import com.monou.infrastructure.persistent.po.Task;
import com.monou.infrastructure.persistent.po.UserBehaviorRebateOrder;
import com.monou.types.enums.ResponseCode;
import com.monou.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Serendipity
 * @description 行为返利服务仓储实现
 * @date 2024-07-24 22:20
 */
@Slf4j
@Component
public class BehaviorRebateRepository implements IBehaviorRebateRepository {

    @Resource
    private IDailyBehaviorRebateDao dailyBehaviorRebateDao;
    @Resource
    private IUserBehaviorRebateOrderDao userBehaviorRebateOrderDao;
    @Resource
    private ITaskDao taskDao;
    @Resource
    private IDBRouterStrategy dbRouter;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private EventPublisher eventPublisher;

    @Override
    public List<DailyBehaviorRebateVO> queryDailyBehaviorRebateConfig(BehaviorTypeVO behaviorTypeVO) {
        List<DailyBehaviorRebate> dailyBehaviorRebates = dailyBehaviorRebateDao.queryDailyBehaviorRebateByBehaviorType(behaviorTypeVO.getCode());
        List<DailyBehaviorRebateVO> dailyBehaviorRebateVOList = new ArrayList<>(dailyBehaviorRebates.size());
        for (DailyBehaviorRebate dailyBehaviorRebate : dailyBehaviorRebates) {
            dailyBehaviorRebateVOList.add(buildDailyBehaviorRebateVO(dailyBehaviorRebate));
        }
        return dailyBehaviorRebateVOList;
    }

    private DailyBehaviorRebateVO buildDailyBehaviorRebateVO(DailyBehaviorRebate dailyBehaviorRebate) {
        return DailyBehaviorRebateVO.builder()
                .behaviorType(dailyBehaviorRebate.getBehaviorType())
                .rebateDesc(dailyBehaviorRebate.getRebateDesc())
                .rebateType(dailyBehaviorRebate.getRebateType())
                .rebateConfig(dailyBehaviorRebate.getRebateConfig())
                .build();
    }

    @Override
    public void saveUserRebateRecord(String userId, List<BehaviorRebateAggregate> behaviorRebateAggregates) {
        try {
            dbRouter.doRouter(userId);
            transactionTemplate.execute(status -> {
                try {
                    behaviorRebateAggregates.forEach(behaviorRebateAggregate -> {
                        // 用户返利订单对象
                        UserBehaviorRebateOrder userBehaviorRebateOrder = buildUserBehaviorRebateOrder(behaviorRebateAggregate);
                        userBehaviorRebateOrderDao.insert(userBehaviorRebateOrder);
                        // 任务对象
                        Task task = buildTask(behaviorRebateAggregate);
                        taskDao.insert(task);
                    });

                    return 1;
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("写入返利记录，唯一索引冲突 userId: {}", userId, e);
                    throw new AppException(ResponseCode.INDEX_DUP.getCode(), ResponseCode.INDEX_DUP.getInfo());
                }
            });
        } finally {
            dbRouter.clear();
        }

        // 同步发送MQ消息
        behaviorRebateAggregates.forEach(behaviorRebateAggregate -> {
            TaskEntity taskEntity = behaviorRebateAggregate.getTaskEntity();
            Task task = new Task();
            task.setUserId(taskEntity.getUserId());
            task.setMessageId(taskEntity.getMessageId());

            try {
                // 发送消息【在事务外执行，如果失败还有任务补偿】
                eventPublisher.publish(taskEntity.getTopic(), taskEntity.getMessage());
                // 更新数据库记录，task 任务表
                taskDao.updateTaskSendMessageCompleted(task);
            } catch (Exception e) {
                log.error("写入返利记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
                taskDao.updateTaskSendMessageFail(task);
            }
        });
    }

    private Task buildTask(BehaviorRebateAggregate behaviorRebateAggregate) {
        TaskEntity taskEntity = behaviorRebateAggregate.getTaskEntity();
        Task task = new Task();
        task.setUserId(taskEntity.getUserId());
        task.setTopic(taskEntity.getTopic());
        task.setMessageId(taskEntity.getMessageId());
        task.setMessage(JSON.toJSONString(taskEntity.getMessage()));
        task.setState(taskEntity.getState().getCode());
        return task;
    }

    private UserBehaviorRebateOrder buildUserBehaviorRebateOrder(BehaviorRebateAggregate behaviorRebateAggregate) {
        BehaviorRebateOrderEntity behaviorRebateOrderEntity = behaviorRebateAggregate.getBehaviorRebateOrderEntity();
        // 用户行为返利订单对象
        UserBehaviorRebateOrder userBehaviorRebateOrder = new UserBehaviorRebateOrder();
        userBehaviorRebateOrder.setUserId(behaviorRebateOrderEntity.getUserId());
        userBehaviorRebateOrder.setOrderId(behaviorRebateOrderEntity.getOrderId());
        userBehaviorRebateOrder.setBehaviorType(behaviorRebateOrderEntity.getBehaviorType());
        userBehaviorRebateOrder.setRebateDesc(behaviorRebateOrderEntity.getRebateDesc());
        userBehaviorRebateOrder.setRebateType(behaviorRebateOrderEntity.getRebateType());
        userBehaviorRebateOrder.setOutBusinessNo(behaviorRebateOrderEntity.getOutBusinessNo());
        userBehaviorRebateOrder.setRebateConfig(behaviorRebateOrderEntity.getRebateConfig());
        userBehaviorRebateOrder.setBizId(behaviorRebateOrderEntity.getBizId());

        return userBehaviorRebateOrder;
    }

    @Override
    public List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo) {
        // 1. 请求对象
        UserBehaviorRebateOrder userBehaviorRebateOrderReq = new UserBehaviorRebateOrder();
        userBehaviorRebateOrderReq.setUserId(userId);
        userBehaviorRebateOrderReq.setOutBusinessNo(outBusinessNo);
        // 2. 查询结果
        List<UserBehaviorRebateOrder> userBehaviorRebateOrderResList = userBehaviorRebateOrderDao.queryOrderByOutBusinessNo(userBehaviorRebateOrderReq);
        List<BehaviorRebateOrderEntity> behaviorRebateOrderEntities = new ArrayList<>(userBehaviorRebateOrderResList.size());
        for (UserBehaviorRebateOrder userBehaviorRebateOrder : userBehaviorRebateOrderResList) {
            BehaviorRebateOrderEntity behaviorRebateOrderEntity = buildBehaviorRebateOrderEntity(userBehaviorRebateOrder);
            behaviorRebateOrderEntities.add(behaviorRebateOrderEntity);
        }
        return behaviorRebateOrderEntities;
    }

    private BehaviorRebateOrderEntity buildBehaviorRebateOrderEntity(UserBehaviorRebateOrder userBehaviorRebateOrder) {
        return BehaviorRebateOrderEntity.builder()
                .userId(userBehaviorRebateOrder.getUserId())
                .orderId(userBehaviorRebateOrder.getOrderId())
                .behaviorType(userBehaviorRebateOrder.getBehaviorType())
                .rebateDesc(userBehaviorRebateOrder.getRebateDesc())
                .rebateType(userBehaviorRebateOrder.getRebateType())
                .rebateConfig(userBehaviorRebateOrder.getRebateConfig())
                .outBusinessNo(userBehaviorRebateOrder.getOutBusinessNo())
                .bizId(userBehaviorRebateOrder.getBizId())
                .build();
    }

}
