package com.monou.domain.activity.service.rule.factory;

import com.monou.domain.activity.service.rule.IActionChain;
import com.monou.domain.activity.service.rule.IActionChainArmory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Serendipity
 * @description 责任链工厂
 * @date 2024-07-15 23:14
 **/
@Service
public class DefaultActivityChainFactory {

    private final IActionChain actionChain;

    /**
     * 组装活动校验责任链
     * @param actionChainGroup 活动校验类的所有map
     */
    public DefaultActivityChainFactory(Map<String, IActionChain> actionChainGroup) {

        actionChain = actionChainGroup.get(ActionModel.activity_base_action.code);
        actionChain.appendNext(actionChainGroup.get(ActionModel.activity_sku_stock_action.code));
    }

    /**
     * 向外部返回责任链
     *
     * @return 责任链的头节点
     */
    public IActionChain openActionChain() {
        return this.actionChain;
    }


    @Getter
    @AllArgsConstructor
    public enum ActionModel {
        /**
         *
         */
        activity_base_action("activity_base_action", "活动的库存、时间校验"),
        activity_sku_stock_action("activity_sku_stock_action", "活动sku库存"),
        ;

        private final String code;
        private final String info;

    }
}

