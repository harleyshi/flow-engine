package com.flow.engine.node.definition.parser;

import cn.hutool.core.collection.CollectionUtil;
import com.flow.engine.node.AbstractComponent;
import com.flow.engine.node.ChooseComponent;
import com.flow.engine.node.IfComponent;
import com.flow.engine.operator.Condition;
import com.flow.engine.operator.Operator;
import com.flow.engine.node.builder.Builders;
import com.flow.engine.node.builder.CaseBuilder;
import com.flow.engine.node.builder.ChooseBuilder;
import com.flow.engine.model.EngineModel;
import com.flow.engine.model.FlowCtx;
import com.flow.engine.node.definition.NodeDefinition;
import com.flow.engine.node.definition.ChooseNodeDefinition;

import java.util.List;
import java.util.Map;

/**
 * choose多分支条件解析器
 * @author harley.shi
 * @date 2024/6/25
 */
public class ChooseComponentParser<C extends FlowCtx> extends AbstractParser<C> {

    @Override
    public AbstractComponent<C> doParse(EngineModel<C> model, NodeDefinition definition) {
        ChooseNodeDefinition chooseDefinition = (ChooseNodeDefinition) definition;
        String test = chooseDefinition.getTest();
        Condition<?, C> scriptOperator = model.getScript(test);
        ChooseBuilder<C> chooseBuilder = Builders.newChoose(test);
        if(scriptOperator != null){
            chooseBuilder.test(scriptOperator);
        }else{
            Condition<?, C> condition = builderCondition(test);
            chooseBuilder.test(condition);
        }
        // 处理默认分支
        List<NodeDefinition> defaultDefList = chooseDefinition.getDefaultDef();
        if (CollectionUtil.isNotEmpty(defaultDefList)) {
            chooseBuilder.defaultBranch(builderComponents(model, defaultDefList));
        }

        // 处理条件分支
        Map<String, List<NodeDefinition>> caseMap = chooseDefinition.getCaseMap();
        caseMap.forEach((when, subElements) -> {
            CaseBuilder<C> caseBuilder = chooseBuilder.when(when);
            caseBuilder.then(builderComponents(model, subElements));
        });
        ChooseComponent<C> chooseComponent =  chooseBuilder.build();
        chooseComponent.setTimeout(chooseDefinition.getTimeout());
        chooseComponent.setIgnoreException(chooseDefinition.isIgnoreException());
        return chooseComponent;
    }
}
