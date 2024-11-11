package com.flow.engine.node.builder;


import com.flow.engine.node.AbstractComponent;
import com.flow.engine.node.PipelineComponent;
import com.flow.engine.model.FlowCtx;
import com.flow.engine.utils.AssertUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * pipeline构造器
 * @author harley.shi
 * @date 2024/7/1
 */
public class PipelineBuilder<C extends FlowCtx> implements Builder<C> {
    private final String name;

    private final List<AbstractComponent<C>> components = new ArrayList<>();

    public PipelineBuilder(String name) {
        this.name = name;
    }

    public PipelineBuilder<C> next(AbstractComponent<C> component) {
        AssertUtil.notNull(component, "must not be null");
        this.components.add(component);
        return this;
    }

    public PipelineBuilder<C> next(PipelineComponent<C> component) {
        AssertUtil.notNull(component, "component must not be null");
        AssertUtil.notEmpty(component.getPipeline(), "component pipeline must not be null!");
        this.components.add(component);
        return this;
    }

    public PipelineComponent<C> build() {
        AssertUtil.notEmpty(components, "pipeline's components size must greater than zero");

        PipelineComponent<C> pipelineComponent = new PipelineComponent<>(name);
        pipelineComponent.setName(name);
        pipelineComponent.setPipeline(components);
        return pipelineComponent;
    }

}
