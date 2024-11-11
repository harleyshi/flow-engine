package com.flow.engine;

import com.flow.engine.model.FlowCtx;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author harley.shi
 * @date 2024/10/30
 */
public class FlowExecutorRegister {
    private static final FlowExecutorRegister REGISTER = new FlowExecutorRegister();

    private final Map<String, FlowExecutor<FlowCtx>> flowExecutors = new ConcurrentHashMap<>();

    private FlowExecutorRegister(){}

    public static FlowExecutorRegister getInstance() {
        return REGISTER;
    }

    public void register(FlowExecutor<FlowCtx> flowEngine) {
        flowExecutors.put(flowEngine.getName(), flowEngine);
    }

    public FlowExecutor<FlowCtx> get(String flowName) {
        return flowExecutors.get(flowName);
    }

}
