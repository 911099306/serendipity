package com.monou.domain.activity.service.product;

import com.monou.domain.activity.model.entity.SkuProductEntity;
import com.monou.domain.activity.respository.IActivityRepository;
import com.monou.domain.activity.service.IRaffleActivitySkuProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Serendipity
 * @description sku商品服务
 * @date 2024-07-27 21:49
 **/
@Service
public class RaffleActivitySkuProductService implements IRaffleActivitySkuProductService {

    @Resource
    private IActivityRepository repository;

    @Override
    public List<SkuProductEntity> querySkuProductEntityListByActivityId(Long activityId) {
        return repository.querySkuProductEntityListByActivityId(activityId);
    }
}
