package com.flow.engine.node;

import cn.hutool.core.collection.CollectionUtil;
import com.flow.engine.model.FlowCtx;

import java.util.List;

/**
 * @author harley.shi
 * @date 2024/7/1
 */
public class Invoker {

    public static <C extends FlowCtx> void invoke(C context, AbstractComponent<C> component){
        component.execute(context);
    }

    public static <C extends FlowCtx> void invoke(C context, List<AbstractComponent<C>> components) {
        if(CollectionUtil.isEmpty(components)){
            return;
        }
        for (AbstractComponent<C> component : components) {
            invoke(context, component);
        }
    }
}
