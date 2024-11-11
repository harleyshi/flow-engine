package com.flow.engine.xml;

import com.flow.engine.FlowExecutor;
import com.flow.engine.FlowExecutorRegister;
import com.flow.engine.model.FlowCtx;
import com.flow.engine.exception.FlowException;

/**
 * @author harley.shi
 * @date 2024/11/4
 */
public class EngineLoader {

    private final FlowParser flowParser = new XmlFlowParser();

    /**
     * 解析xml内容并注册到FlowExecutorRegister中
     */
    public void load(String xmlContent) {
        try {
            FlowExecutor<FlowCtx> engine = flowParser.parse(xmlContent);
            FlowExecutorRegister.getInstance().register(engine);
        }catch (Exception e){
            throw new FlowException("xml 内容解析失败", e);
        }
    }
}
