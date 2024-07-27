package com.monou.infrastructure.persistent.dao;

import com.monou.infrastructure.persistent.po.UserCreditAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Serendipity
 * @description 用户积分账户
 * @date 2024-07-26 00:07
 */
@Mapper
public interface IUserCreditAccountDao {

    void insert(UserCreditAccount userCreditAccountReq);

    int updateAddAmount(UserCreditAccount userCreditAccountReq);

    /**
     * 查询用户积分制
     *
     * @param userCreditAccountReq 请求信息
     * @return 用户积分信息
     */
    UserCreditAccount queryUserCreditAccount(UserCreditAccount userCreditAccountReq);


}