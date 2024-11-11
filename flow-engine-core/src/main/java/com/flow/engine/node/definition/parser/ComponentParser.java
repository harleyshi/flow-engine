package com.flow.engine.node.definition.parser;

import com.flow.engine.node.AbstractComponent;
import com.flow.engine.operator.Operator;
import com.flow.engine.node.StandardComponent;
import com.flow.engine.model.EngineModel;
import com.flow.engine.model.FlowCtx;
import com.flow.engine.node.definition.NodeDefinition;
import com.flow.engine.node.definition.ComponentNodeDefinition;
import org.apache.commons.lang3.StringUtils;

/**
 * 标准组件解析器
 * @author harley.shi
 * @date 2024/6/25
 */
public class ComponentParser<C extends FlowCtx> extends AbstractParser<C> {

    @Override
    public AbstractComponent<C> doParse(EngineModel<C> model, NodeDefinition definition) {
        ComponentNodeDefinition componentDefinition = (ComponentNodeDefinition) definition;
        // 正常流程算子
        String opName = componentDefinition.getOperator();

        String normalParamsKey = componentDefinition.getParams();
        String normalParamsValue = null;
        if(StringUtils.isNotBlank(normalParamsKey)){
            normalParamsValue = model.getParams(normalParamsKey);
        }
        Operator<C> operator = builderOperator(opName, normalParamsValue);
        StandardComponent<C> component = new StandardComponent<>(opName);
        component.setOperator(operator);

        // 用于回滚的算子
        String rollback = componentDefinition.getRollback();
        if(rollback != null){
            String rollbackParamsKey = componentDefinition.getRollbackParams();
            String rollbackParamsValue = null;
            if(StringUtils.isNotBlank(rollbackParamsKey)){
                rollbackParamsValue = model.getParams(rollbackParamsKey);
            }
            Operator<C> rollbackInvoker = builderOperator(rollback, rollbackParamsValue);
            component.setRollback(rollbackInvoker);
        }
        component.setDesc(componentDefinition.getDesc());
        component.setTimeout(componentDefinition.getTimeout());
        component.setIgnoreException(componentDefinition.isIgnoreException());
        return component;
    }
}
