package com.monou.infrastructure.persistent.dao;

import com.monou.infrastructure.persistent.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author serendipity
 * @description 奖品表DAO
 * @date 2024-06-02
 */
@Mapper
public interface IAwardDao {

    List<Award> queryAwardList();

    String queryAwardConfigByAwardId(Integer awardId);

    String queryAwardKeyByAwardId(Integer awardId);

}
