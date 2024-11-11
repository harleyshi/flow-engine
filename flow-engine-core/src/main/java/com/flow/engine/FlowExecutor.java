package com.flow.engine;

import com.flow.engine.operator.Operator;
import com.flow.engine.node.PipelineComponent;
import com.flow.engine.model.FlowCtx;
import com.flow.engine.utils.AssertUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

/**
 * 代表一个完整的流程
 * @author harley.shi
 * @date 2024/7/3
 */
@Slf4j
public class FlowExecutor<C extends FlowCtx> {

    @Getter
    private final String name;

    private final PipelineComponent<C> pipelineComponent;

    public FlowExecutor(String name, PipelineComponent<C> pipelineComponent) {
        this.name = name;
        this.pipelineComponent = pipelineComponent;
    }

    public void execute(C context){
        AssertUtil.notNull(context, "context must not be null!");
        try{
            // 执行正常流程
            pipelineComponent.execute(context);
        }catch (Exception e){
            // 有异常：执行回滚逻辑
            if(context.hasException()){
                executeRollback(context);
            }
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public void executeRollback(C context) {
        Stack<Operator<?>> rollbackStack = context.rollbackStacks();
        while (!rollbackStack.isEmpty()){
            Operator<C> invoker = (Operator<C>) rollbackStack.pop();
            if(invoker == null){
                break;
            }
            try{
                invoker.exec(context);
            }catch (Exception e){
                // TODO 回滚失败，做特殊处理
                log.error("[{}] executeRollback error", invoker.getClass(), e);
            }
        }
    }
}
