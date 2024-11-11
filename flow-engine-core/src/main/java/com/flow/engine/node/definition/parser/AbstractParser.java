package com.flow.engine.node.definition.parser;

import cn.hutool.core.collection.CollectionUtil;
import com.flow.engine.node.AbstractComponent;
import com.flow.engine.operator.Condition;
import com.flow.engine.operator.Operator;
import com.flow.engine.model.EngineModel;
import com.flow.engine.model.FlowCtx;
import com.flow.engine.operator.OperatorsRegister;
import com.flow.engine.node.definition.NodeDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * 组件元素解析器
 * @author harley.shi
 * @date 2024/6/25
 */
public abstract class AbstractParser<C extends FlowCtx> {

    public abstract AbstractComponent<C> doParse(EngineModel<C> model, NodeDefinition definition);

    @SuppressWarnings("unchecked")
    protected Operator<C> builderOperator(String operator, String params) {
        OperatorsRegister operatorsRegister = OperatorsRegister.getInstance();
        return (Operator<C>) operatorsRegister.getOperatorDefine(operator).builder(params);
    }

    @SuppressWarnings("unchecked")
    protected Condition<?, C> builderCondition(String operator) {
        OperatorsRegister operatorsRegister = OperatorsRegister.getInstance();
        return (Condition<?, C>) operatorsRegister.getOperatorDefine(operator).builder(null);
    }

    protected AbstractComponent<C> builderComponent(EngineModel<C> model, NodeDefinition node) {
        return model.getComponentParser(node.nodeType()).doParse(model, node);
    }

    protected List<AbstractComponent<C>> builderComponents(EngineModel<C> model, List<NodeDefinition> definitions) {
        if(CollectionUtil.isEmpty(definitions)){
            return null;
        }
        List<AbstractComponent<C>> components = new ArrayList<>();
        for (NodeDefinition node : definitions) {
            components.add(builderComponent(model, node));
        }
        return components;
    }
}
