package com.flow.engine.demo;

import cn.hutool.extra.spring.SpringUtil;
import com.flow.engine.FlowExecutor;
import com.flow.engine.FlowExecutorRegister;
import com.flow.engine.model.FlowCtx;
import com.flow.engine.demo.context.OrderContext;
import com.flow.engine.demo.domain.TestADO;
import com.flow.engine.demo.domain.TestBDO;
import com.flow.engine.demo.domain.TestCDO;
import com.flow.engine.demo.functions.OrderFunctions;
import com.flow.engine.demo.mapper.TestAMapper;
import com.flow.engine.demo.mapper.TestBMapper;
import com.flow.engine.demo.mapper.TestCMapper;
import com.flow.engine.node.ChooseComponent;
import com.flow.engine.node.PipelineComponent;
import com.flow.engine.node.StandardComponent;
import com.flow.engine.node.builder.Builders;
import com.flow.engine.node.builder.ChooseBuilder;
import com.flow.engine.node.builder.EngineBuilder;
import com.flow.engine.node.builder.PipelineBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootApplication
public class FlowDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowDemoApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeFlowEngine(ApplicationReadyEvent event) {
        System.out.println("服务启动成功...........");

        usingClasspathXmlProcessParser2();
    }

    public void usingClasspathXmlProcessParser2() {
        PipelineBuilder<OrderContext> subChoosePipBuilder = Builders.pipeline("subChoosePip");
        PipelineComponent<OrderContext> subChoosePip  = subChoosePipBuilder
                .next(new StandardComponent<>("Choose流程2-1", (ctx) -> {
                    System.out.println("子节点：Choose流程2-1");
                }))
                .next(new StandardComponent<>("Choose流程2-2", (ctx) -> {
                    System.out.println("子节点：Choose流程2-2");
                }))
                .build();

        ChooseBuilder<OrderContext> chooseComponentBuild = Builders.newChoose("testChoose");
        ChooseComponent<OrderContext> chooseComponent = chooseComponentBuild
                .test(ctx -> {
                    System.out.println("执行条件判断");
                    return 211;
                })
                .when(1).then(new StandardComponent<>("testThen1", (ctx) -> {
                    System.out.println("执行条件1分支");
                }))
                .when(2).then(new StandardComponent<>("testThen2", (ctx) -> {
                    System.out.println("执行条件2分支");
                }))
                .when(3).then(subChoosePip)
                .defaultBranch(new StandardComponent<>("testDefaultBranch", (ctx) -> {
                    System.out.println("执行条件3分支");
                }))
                .build();

        PipelineBuilder<OrderContext> subPipelineBuild = Builders.pipeline("subPipelineBuild");

        PipelineComponent<OrderContext> subPipeline = subPipelineBuild
                .next(new StandardComponent<>("子节点：标准流程1", (ctx) -> {
                    System.out.println("子节点：标准流程1");
                }))
                .next(new StandardComponent<>("子节点：标准流程2", (ctx) -> {
                    System.out.println("子节点：标准流程2");
                }))
                .build();

        PipelineBuilder<OrderContext> pipelineBuilder = Builders.pipeline("pipeline");

        PipelineComponent<OrderContext> pipeline = pipelineBuilder
                .next(subPipeline)
                .next(chooseComponent)
                .build();

        EngineBuilder<OrderContext> engineBuilder = Builders.engine();
        FlowExecutor<OrderContext> engine = engineBuilder.name("testEngine")
                .pipeline(pipeline)
                .build();

        OrderContext context = new OrderContext();
        context.setOrderId("123456");
        context.setOrderName("测试订单");
        engine.execute(context);

        System.out.println(1);
    }

    public static void testFlow() {
        // 执行业务逻辑
        FlowExecutor<FlowCtx> flowExecutor = FlowExecutorRegister.getInstance().get("createOrder");
        if(flowExecutor == null){
            return;
        }
        OrderContext ctx = new OrderContext();
        ctx.setOrderId("123456");
        ctx.setOrderName("测试订单");
        flowExecutor.execute(ctx);
    }

    public static void testTransaction() {
        TestAMapper testAMapper = SpringUtil.getBean(TestAMapper.class);
        TestBMapper testBMapper = SpringUtil.getBean(TestBMapper.class);
        TestCMapper testCMapper = SpringUtil.getBean(TestCMapper.class);
        TransactionTemplate transactionTemplate = SpringUtil.getBean(TransactionTemplate.class);
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(@NotNull TransactionStatus status) {
                try {
                    testAMapper.insert(new TestADO("testA"));
                    testBMapper.insert(new TestBDO("testB"));
                    int i = 1/0;
                    testCMapper.insert(new TestCDO("testC"));
                    return null;
                } catch (Throwable e) {
                    status.setRollbackOnly(); // 发生异常时回滚
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static void testTransaction1() {
        OrderFunctions orderFunctions = SpringUtil.getBean(OrderFunctions.class);

        TransactionTemplate transactionTemplate = SpringUtil.getBean(TransactionTemplate.class);
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(@NotNull TransactionStatus status) {
                try {
                    OrderContext ctx = new OrderContext();
                    ctx.setOrderId("123456");
                    ctx.setOrderName("测试订单");
                    orderFunctions.saveOrderInfo("").exec(ctx);
                    return null;
                } catch (Throwable e) {
                    status.setRollbackOnly(); // 发生异常时回滚
                    throw new RuntimeException(e);
                }
            }
        });
    }


}