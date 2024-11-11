package com.flow.engine.node.builder;


import com.flow.engine.model.FlowCtx;

public final class Builders {

    public static <C extends FlowCtx> ChooseBuilder<C> newChoose(String name) {
        return new ChooseBuilder<>(name);
    }

    public static <C extends FlowCtx> IfBuilder<C> newIf(String name) {
        return new IfBuilder<>(name);
    }

    public static <C extends FlowCtx> PipelineBuilder<C> pipeline(String name) {
        return new PipelineBuilder<>(name);
    }

    public static <T, C extends FlowCtx> ScriptBuilder<T, C> script() {
        return new ScriptBuilder<>();
    }

    public static <C extends FlowCtx> EngineBuilder<C> engine() {
        return new EngineBuilder<>();
    }
}
