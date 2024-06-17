package com.monou.domain.strategy.service.rule.chain;

/**
 * @author Serendipity
 * @description 责任链装配
 * @date 2024-06-17 22:12
 **/
public interface ILogicChainArmory {

    ILogicChain next();

    ILogicChain appendNext(ILogicChain next);

}
