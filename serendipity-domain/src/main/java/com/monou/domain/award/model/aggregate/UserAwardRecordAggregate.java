package com.monou.domain.award.model.aggregate;

import com.monou.domain.award.model.entity.TaskEntity;
import com.monou.domain.award.model.entity.UserAwardRecordEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Serendipity
 * @description 用户中奖记录聚合对象 【聚合代表一个事务操作】
 * @date 2024-07-21 23:01
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAwardRecordAggregate {

    private UserAwardRecordEntity userAwardRecordEntity;

    private TaskEntity taskEntity;
}
