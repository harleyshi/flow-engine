package com.flow.engine.node.builder;


import com.flow.engine.node.AbstractComponent;
import com.flow.engine.model.FlowCtx;

/**
 * 构造器接口，用于构建组件
 * @author harley.shi
 * @date 2024/7/1
 */
public interface Builder< C extends FlowCtx> {

   AbstractComponent<C> build();

}
