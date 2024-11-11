package com.flow.engine.xml;


import com.flow.engine.FlowExecutor;
import com.flow.engine.model.FlowCtx;

/**
 * 流程解析器接口
 * @author harley.shi
 * @date 2024/6/25
 */
public interface FlowParser {

    <C extends FlowCtx> FlowExecutor<C> parse(String content) throws Exception;
}
