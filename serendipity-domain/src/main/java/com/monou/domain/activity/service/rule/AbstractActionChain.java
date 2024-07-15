package com.monou.domain.activity.service.rule;

/**
 * @author Serendipity
 * @description 下单规则责任链抽象类
 * @date 2024-07-15 23:09
 **/
public abstract class AbstractActionChain implements IActionChain{

    private IActionChain next;

    @Override
    public IActionChain next() {
        return next;
    }


    @Override
    public IActionChain appendNext(IActionChain next) {
        this.next = next;
        return next;
    }
}
