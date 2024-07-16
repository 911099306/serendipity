package com.monou.domain.activity.service.armory;

/**
 * @author Serendipity
 * @description 活动装配预热
 * @date 2024-07-16 23:49
 **/
public interface IActivityArmory {

    /**
     * 将该活动信息装配入redis
     * @param sku 活动配置信息
     * @return 是否装配成功
     */
    boolean assembleActivitySku(Long sku);

}
