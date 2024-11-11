package com.flow.engine.node;

import com.flow.engine.model.FlowCtx;
import com.flow.engine.threadpool.Parallel;
import com.flow.engine.threadpool.ThreadPool;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * pipeline构造器
 * @author harley.shi
 * @date 2024/7/2
 */
@Slf4j
@Getter
@Setter
public class PipelineComponent<C extends FlowCtx> extends AbstractComponent<C> {

    /**
     * 组件描述
     */
    private String desc;

    /**
     * 是否异步
     */
    private boolean async;

    /**
     * 组件列表
     */
    private List<AbstractComponent<C>> pipeline;

    public PipelineComponent(String name) {
        super(name);
    }

    @Override
    public void doExecute(C context) {
        // 异步执行
        if(isAsync()){
            Executor executor = Parallel.getExecutor(ThreadPool.Names.KERNEL);
            List<CompletableFuture<Void>> futures = new ArrayList<>();
            for (AbstractComponent<C> component : pipeline) {
                // TODO 检查是否有问题
                futures.add(CompletableFuture.runAsync(()-> Invoker.invoke(context, component), executor));
            }
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }else{
            // 同步执行
            Invoker.invoke(context, pipeline);
        }
    }
}
