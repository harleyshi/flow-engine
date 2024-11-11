package com.flow.engine.node.builder;


import com.flow.engine.FlowExecutor;
import com.flow.engine.node.PipelineComponent;
import com.flow.engine.model.FlowCtx;
import com.flow.engine.utils.AssertUtil;

/**
 * 引擎构造器
 * @author harley.shi
 * @date 2024/7/1
 */
public class EngineBuilder<C extends FlowCtx> {

    /**
     * 引擎名称
     */
    private String name;

    /**
     * 流程组件
     */
    private PipelineComponent<C> pipelineComponent;

    public EngineBuilder<C> pipeline(PipelineComponent<C> pipeline) {
        AssertUtil.notNull(pipeline, "pipeline must not be null");
        this.pipelineComponent = pipeline;
        return this;
    }

    public EngineBuilder<C> name(String name) {
        AssertUtil.notBlank(name, "name must not be null");
        this.name = name;
        return this;
    }

    public FlowExecutor<C> build() {
        AssertUtil.notBlank(name, "name must not be null");
        AssertUtil.notNull(pipelineComponent, "pipeline must not be null");
        return new FlowExecutor<>(name, pipelineComponent);
    }
}
