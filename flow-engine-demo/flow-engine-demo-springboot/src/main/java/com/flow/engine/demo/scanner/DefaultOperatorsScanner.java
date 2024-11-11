package com.flow.engine.demo.scanner;
import com.flow.engine.operator.OperatorDefinition;
import com.flow.engine.operator.factory.OperatorFactory;
import com.flow.engine.operator.OperatorsRegister;
import com.flow.engine.operator.factory.OperatorFactoryMethod;
import com.flow.engine.operator.ComponentFn;
import com.flow.engine.operator.Operator;
import com.flow.engine.exception.FlowException;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.*;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
/**
 * @author harley.shi
 * @date 2024/11/1
 */
public class DefaultOperatorsScanner implements OperatorsScanner{

    private final String packagePath;

    public DefaultOperatorsScanner(String packagePath){
        this.packagePath = packagePath;
    }

    /**
     * 扫描所有算子，并注册到OperatorsRegister中
     */
    @Override
    public void scannerOperators() {
        Collection<URL> urls = ClasspathHelper.forPackage(packagePath);
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(urls)
                .setScanners(Scanners.MethodsAnnotated)
                .filterInputsBy(new FilterBuilder().includePackage(packagePath)));

        // 获取指定注解的所有类
        Set<Method> methodSet = reflections.getMethodsAnnotatedWith(ComponentFn.class);

        Set<Class<?>> clazzSet = new HashSet<>();
        for (Method m : methodSet) {
            clazzSet.add(m.getDeclaringClass());
        }
        for (Class<?> clazz : clazzSet) {
            try {
                Object bean = clazz.newInstance();
                register(bean);
            } catch (Exception e) {
                throw new FlowException(String.format("[%s] new instance error", clazz.getName()), e);
            }
        }
    }

    private void register(Object bean) {
        for (Method method : bean.getClass().getMethods()) {
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
            ComponentFn mAnnotation = method.getAnnotation(ComponentFn.class);
            String operatorName = StringUtils.isBlank(mAnnotation.name()) ? method.getName() : mAnnotation.name();
            OperatorFactory methodFactory;
            try {
                methodFactory = mAnnotation.builder().newInstance();
            }catch (Exception e){
                throw new FlowException(String.format("[%s] builder实例化失败", operatorName));
            }
            OperatorsRegister operatorsRegister = OperatorsRegister.getInstance();
            if(operatorsRegister.containsKey(operatorName)){
                throw new FlowException(String.format("[%s]重复算子，请检查", operatorName));
            }
            OperatorDefinition invokerDefine = new OperatorDefinition(operatorName, methodFactory, new OperatorFactoryMethod(bean, method));
            operatorsRegister.register(operatorName, invokerDefine);
        }
    }
}
