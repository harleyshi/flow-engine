package com.flow.engine.demo.controller;

import com.flow.engine.FlowExecutor;
import com.flow.engine.FlowExecutorRegister;
import com.flow.engine.demo.context.OrderContext;
import com.flow.engine.model.FlowCtx;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.ScriptEvaluator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author harley.shi
 * @date 2024/11/4
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    private final FlowExecutorRegister flowExecutorRegister =FlowExecutorRegister.getInstance();

    @GetMapping("/create")
    public String create() {
        // 执行业务逻辑
        FlowExecutor<FlowCtx> flowExecutor = flowExecutorRegister.get("createOrder");
        if(flowExecutor == null){
            return "failed";
        }
        OrderContext ctx = new OrderContext();
        ctx.setOrderId("123456");
        ctx.setOrderName("测试订单");
        flowExecutor.execute(ctx);

        return ctx.toString();
    }

    private static final ExpressionEvaluator EXPRESSION_EVALUATOR = new ExpressionEvaluator();
    static {
        EXPRESSION_EVALUATOR.setParameters(new String[] { "a", "b" }, new Class[] { int.class, int.class });
        EXPRESSION_EVALUATOR.setExpressionType(int.class);
        try {
            EXPRESSION_EVALUATOR.cook("a + b");
        } catch (CompileException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/expression")
    public String expression() {
        try {
            int result = (Integer) EXPRESSION_EVALUATOR.evaluate(new Object[] { 19, 23 });
            return result + "";
        }catch (Exception e){
            e.printStackTrace();
            return "failed";
        }
    }

    private static final ScriptEvaluator SCRIPT_EVALUATOR = new ScriptEvaluator();
    static {
        SCRIPT_EVALUATOR.setParameters(new String[] { "arg1", "arg2" }, new Class[] {Integer.class, Integer.class});
        try {
            SCRIPT_EVALUATOR.cook(
                    ""+
                            "int a = arg1;\n" +
                            "int b = arg2;\n" +
                            "System.out.println(a);\n"+
                            "System.out.println(b);\n"+
                            "System.out.println(a+b);\n"
            );
        } catch (CompileException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/script")
    public String script() {
       try {
           SCRIPT_EVALUATOR.evaluate(new Object[]{11, 22});
           return "success";
       }catch (Exception e){
           e.printStackTrace();
           return "failed";
       }
    }
}
