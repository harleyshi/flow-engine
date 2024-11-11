package com.flow.engine.node.definition.parser;

import com.flow.engine.node.AbstractComponent;
import com.flow.engine.node.IfComponent;
import com.flow.engine.operator.Condition;
import com.flow.engine.operator.Operator;
import com.flow.engine.node.builder.Builders;
import com.flow.engine.node.builder.IfBuilder;
import com.flow.engine.model.EngineModel;
import com.flow.engine.model.FlowCtx;
import com.flow.engine.node.definition.NodeDefinition;
import com.flow.engine.node.definition.IfNodeDefinition;

/**
 * 条件组件解析器
 * @author harley.shi
 * @date 2024/6/25
 */
public class IfComponentParser<C extends FlowCtx> extends AbstractParser<C> {

    @Override
    public AbstractComponent<C> doParse(EngineModel<C> model, NodeDefinition definition) {
        IfNodeDefinition ifDefinition = (IfNodeDefinition) definition;
        String test = ifDefinition.getTest();
        IfBuilder<C> ifComponentBuilder = Builders.newIf(test);
        // 脚本不为空优先用脚本
        Condition<?, C> scriptOperator = model.getScript(test);
        if(scriptOperator != null){
            ifComponentBuilder.test(scriptOperator);
        }else{
            Condition<?, C> condition = builderCondition(test);
            ifComponentBuilder.test(condition);
        }
        // 处理if分支
        ifComponentBuilder.then(builderComponents(model, ifDefinition.getIfThen()));

        // 处理else分支
        if(ifDefinition.getIfElse() != null){
            ifComponentBuilder.otherwise(builderComponents(model, ifDefinition.getIfElse()));
        }
        IfComponent<C> ifComponent = ifComponentBuilder.build();
        ifComponent.setTimeout(ifDefinition.getTimeout());
        ifComponent.setIgnoreException(ifDefinition.isIgnoreException());
        return ifComponent;
    }
}
