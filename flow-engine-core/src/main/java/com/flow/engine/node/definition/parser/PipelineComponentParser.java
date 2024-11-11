package com.flow.engine.node.definition.parser;

import com.flow.engine.node.PipelineComponent;
import com.flow.engine.node.builder.Builders;
import com.flow.engine.node.builder.PipelineBuilder;
import com.flow.engine.model.EngineModel;
import com.flow.engine.model.FlowCtx;
import com.flow.engine.node.definition.NodeDefinition;
import com.flow.engine.node.definition.PipelineNodeDefinition;

import java.util.List;

/**
 * pipeline 组件解析器
 * @author harley.shi
 * @date 2024/6/25
 */
public class PipelineComponentParser<C extends FlowCtx> extends AbstractParser<C> {

    @Override
    public PipelineComponent<C> doParse(EngineModel<C> model, NodeDefinition definition) {
        PipelineNodeDefinition pipelineDefinition = (PipelineNodeDefinition) definition;
        PipelineBuilder<C> subPipelineBuilder = Builders.pipeline(definition.name());
        List<NodeDefinition> children = pipelineDefinition.getChildren();
        for (NodeDefinition child : children) {
            subPipelineBuilder.next(builderComponent(model, child));
        }
        PipelineComponent<C> component = subPipelineBuilder.build();
        component.setName(pipelineDefinition.getName());
        component.setDesc(pipelineDefinition.getDesc());
        component.setAsync(pipelineDefinition.isAsync());
        component.setTimeout(pipelineDefinition.getTimeout());
        component.setIgnoreException(pipelineDefinition.isIgnoreException());
        return component;
    }
}
