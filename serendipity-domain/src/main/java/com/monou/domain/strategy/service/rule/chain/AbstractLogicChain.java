package com.monou.domain.strategy.service.rule.chain;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Serendipity
 * @description 抽奖策略责任链，判断走那种抽奖策略。如；默认抽象、权重抽奖、黑名单抽奖
 * @date 2024-06-17 22:12
 **/
@Slf4j
public abstract class AbstractLogicChain implements ILogicChain{

    private ILogicChain next;

    @Override
    public ILogicChain next() {
        return next;
    }

    @Override
    public ILogicChain appendNext(ILogicChain next) {
        this.next = next;
        return next;
    }

    /**
     * 返回当前bean负责的规则类型
     * @return 规则类型
     */
    protected abstract String ruleModel();
}
