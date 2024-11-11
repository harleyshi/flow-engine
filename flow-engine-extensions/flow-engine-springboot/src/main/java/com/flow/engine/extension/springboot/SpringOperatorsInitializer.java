package com.flow.engine.extension.springboot;

import com.flow.engine.FlowReader;
import com.flow.engine.operator.ComponentFn;
import com.flow.engine.operator.Operator;
import com.flow.engine.operator.OperatorDefinition;
import com.flow.engine.operator.OperatorsRegister;
import com.flow.engine.operator.factory.OperatorFactory;
import com.flow.engine.operator.factory.OperatorFactoryMethod;
import com.flow.engine.exception.FlowException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;

import java.lang.reflect.Method;

/**
 * @author harley.shi
 * @date 2024/11/4
 */
public class SpringOperatorsInitializer implements SmartInitializingSingleton, ApplicationContextAware {
    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext context;

    private final FlowReader flowReader;

    public SpringOperatorsInitializer(FlowReader flowReader) {
        this.flowReader = flowReader;
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        SpringOperatorsInitializer.context = ctx;
    }

    @Override
    public void afterSingletonsInstantiated() {
        if (context == null) {
            return;
        }
        String[] beanDefinitionNames = context.getBeanNamesForType(Object.class, false, true);
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean;
            Lazy onBean = context.findAnnotationOnBean(beanDefinitionName, Lazy.class);
            if (onBean != null){
                continue;
            }else {
                bean = context.getBean(beanDefinitionName);
            }
            try {
                register(bean);
            } catch (Exception e) {
                throw new FlowException(String.format("[%s] bean register error", bean.getClass().getName()), e);
            }
        }

        // 初始化脚本引擎
        flowReader.load();
    }

    private void register(Object bean) {
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
        for (Method method : targetClass.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(ComponentFn.class)) {
                continue;
            }
            // 参数不能超过1个
            if(method.getParameterCount() > 1){
                throw new FlowException(String.format("[%s#%s] 参数不能超过1个", bean.getClass(), method.getName()));
            }
            Class<?> returnType = method.getReturnType();
            if(!returnType.equals(Operator.class)){
                throw new FlowException(String.format("[%s#%s] 方法必须返回Invoker函数", bean.getClass(), method.getName()));
            }
            OperatorsRegister operatorsRegister = OperatorsRegister.getInstance();
            ComponentFn mAnnotation = method.getAnnotation(ComponentFn.class);
            String operatorName = StringUtils.isBlank(mAnnotation.name()) ? method.getName() : mAnnotation.name();
            OperatorFactory methodFactory;
            try {
                methodFactory = mAnnotation.builder().newInstance();
            }catch (Exception e){
                throw new FlowException(String.format("[%s] builder实例化失败", operatorName));
            }
            if(operatorsRegister.containsKey(operatorName)){
                throw new FlowException(String.format("[%s]重复算子，请检查", operatorName));
            }
            OperatorDefinition invokerDefine = new OperatorDefinition(operatorName, methodFactory, new OperatorFactoryMethod(bean, method));
            operatorsRegister.register(operatorName, invokerDefine);
        }
    }
}
