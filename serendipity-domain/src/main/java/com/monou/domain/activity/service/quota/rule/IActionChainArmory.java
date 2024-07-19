package com.monou.domain.activity.service.quota.rule;

/**
 * @author Serendipity
 * @description 抽奖动作责任链装配
 * @date 2024-07-15 23:07
 **/
public interface IActionChainArmory {

    /**
     * 责任链下一处理节点
     *
     * @return 责任链节点
     */
    IActionChain next();

    /**
     * 增加责任链下一个处理节点
     * @param next 处理节点
     * @return 下一节点
     */
    IActionChain appendNext(IActionChain next);

}
