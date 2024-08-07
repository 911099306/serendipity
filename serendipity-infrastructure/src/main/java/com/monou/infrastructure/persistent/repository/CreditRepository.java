package com.monou.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import com.alibaba.fastjson.JSON;
import com.monou.domain.award.model.valobj.AccountStatusVO;
import com.monou.domain.credit.model.aggregate.TradeAggregate;
import com.monou.domain.credit.model.entity.CreditAccountEntity;
import com.monou.domain.credit.model.entity.CreditOrderEntity;
import com.monou.domain.credit.model.entity.TaskEntity;
import com.monou.domain.credit.repository.ICreditRepository;
import com.monou.infrastructure.event.EventPublisher;
import com.monou.infrastructure.persistent.dao.ITaskDao;
import com.monou.infrastructure.persistent.dao.IUserCreditAccountDao;
import com.monou.infrastructure.persistent.dao.IUserCreditOrderDao;
import com.monou.infrastructure.persistent.po.Task;
import com.monou.infrastructure.persistent.po.UserCreditAccount;
import com.monou.infrastructure.persistent.po.UserCreditOrder;
import com.monou.infrastructure.persistent.redis.IRedisService;
import com.monou.types.common.Constants;
import com.monou.types.enums.ResponseCode;
import com.monou.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @author Serendipity
 * @description 用户积分仓储
 * @date 2024-07-27 00:43
 */
@Slf4j
@Repository
public class CreditRepository implements ICreditRepository {

    @Resource
    private IRedisService redisService;
    @Resource
    private IUserCreditAccountDao userCreditAccountDao;
    @Resource
    private IUserCreditOrderDao userCreditOrderDao;
    @Resource
    private ITaskDao taskDao;
    @Resource
    private IDBRouterStrategy dbRouter;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private EventPublisher eventPublisher;



    @Override
    public void saveUserCreditTradeOrder(TradeAggregate tradeAggregate) {
        String userId = tradeAggregate.getUserId();
        CreditAccountEntity creditAccountEntity = tradeAggregate.getCreditAccountEntity();
        CreditOrderEntity creditOrderEntity = tradeAggregate.getCreditOrderEntity();
        TaskEntity taskEntity = tradeAggregate.getTaskEntity();


        // 积分账户
        UserCreditAccount userCreditAccountReq = buildUserCreditAccount(userId, creditAccountEntity);

        // 积分订单
        UserCreditOrder userCreditOrderReq = UserCreditOrder.buildUserCreditOrder(creditOrderEntity.getUserId(), creditOrderEntity.getOrderId(), creditOrderEntity.getTradeName().getName(), creditOrderEntity.getTradeType().getCode(), creditOrderEntity.getTradeAmount(), creditOrderEntity.getOutBusinessNo());



        Task task = new Task();
        task.setUserId(taskEntity.getUserId());
        task.setTopic(taskEntity.getTopic());
        task.setMessageId(taskEntity.getMessageId());
        task.setMessage(JSON.toJSONString(taskEntity.getMessage()));
        task.setState(taskEntity.getState().getCode());

        RLock lock = redisService.getLock(Constants.RedisKey.USER_CREDIT_ACCOUNT_LOCK + userId + Constants.UNDERLINE + creditOrderEntity.getOutBusinessNo());
        try {
            lock.lock(3, TimeUnit.SECONDS);
            dbRouter.doRouter(userId);
            // 编程式事务
            transactionTemplate.execute(status -> {
                try {
                    // 1. 保存账户积分
                    UserCreditAccount userCreditAccount = userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);
                    if (userCreditAccount == null) {
                        userCreditAccountDao.insert(userCreditAccountReq);
                    } else {
                        BigDecimal availableAmount = userCreditAccountReq.getAvailableAmount();
                        if (availableAmount.compareTo(BigDecimal.ZERO) >= 0) {
                            userCreditAccountDao.updateAddAmount(userCreditAccountReq);
                        } else {
                            int subtractionCount = userCreditAccountDao.updateSubtractionAmount(userCreditAccountReq);
                            if (1 != subtractionCount) {
                                status.setRollbackOnly();
                                throw new AppException(ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_AMOUNT.getCode(), ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_AMOUNT.getInfo());
                            }
                        }
                    }
                    // 2. 保存账户订单
                    userCreditOrderDao.insert(userCreditOrderReq);
                    // 3. 写入任务
                    taskDao.insert(task);
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("调整账户积分额度异常，唯一索引冲突 userId:{} orderId:{}", userId, creditOrderEntity.getOrderId(), e);
                } catch (Exception e) {
                    status.setRollbackOnly();
                    log.error("调整账户积分额度失败 userId:{} orderId:{}", userId, creditOrderEntity.getOrderId(), e);
                }
                return 1;
            });
        } finally {
            dbRouter.clear();
            if (lock.isLocked()) {
                lock.unlock();
            }
        }

        try {
            // 发送消息【在事务外执行，如果失败还有任务补偿】
            eventPublisher.publish(task.getTopic(), task.getMessage());
            // 更新数据库记录，task 任务表
            taskDao.updateTaskSendMessageCompleted(task);
            log.info("调整账户积分记录，发送MQ消息完成 userId: {} orderId:{} topic: {}", userId, creditOrderEntity.getOrderId(), task.getTopic());
        } catch (Exception e) {
            log.error("调整账户积分记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
            taskDao.updateTaskSendMessageFail(task);
        }

    }



    private UserCreditAccount buildUserCreditAccount(String userId, CreditAccountEntity creditAccountEntity) {
        UserCreditAccount userCreditAccountReq = new UserCreditAccount();
        userCreditAccountReq.setUserId(userId);
        userCreditAccountReq.setTotalAmount(creditAccountEntity.getAdjustAmount());
        // 知识；仓储往上有业务语义，仓储往下到 dao 操作是没有业务语义的。所以不用在乎这块使用的字段名称，直接用持久化对象即可。
        userCreditAccountReq.setAvailableAmount(creditAccountEntity.getAdjustAmount());
        userCreditAccountReq.setAccountStatus(AccountStatusVO.open.getCode());
        return userCreditAccountReq;
    }


    @Override
    public CreditAccountEntity queryUserCreditAccount(String userId) {
        UserCreditAccount userCreditAccountReq = new UserCreditAccount();
        userCreditAccountReq.setUserId(userId);
        try {
            dbRouter.doRouter(userId);
            UserCreditAccount userCreditAccount = userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);
            BigDecimal availableAmount = BigDecimal.ZERO;
            if (null != userCreditAccount) {
                availableAmount = userCreditAccount.getAvailableAmount();
            }
            return CreditAccountEntity.builder().userId(userId).adjustAmount(availableAmount).build();

        } finally {
            dbRouter.clear();
        }

    }

}

