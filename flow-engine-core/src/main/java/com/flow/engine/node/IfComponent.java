package com.flow.engine.node;

import com.flow.engine.model.FlowCtx;
import com.flow.engine.operator.Condition;
import com.flow.engine.operator.Operator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Setter
public class IfComponent<C extends FlowCtx> extends AbstractComponent<C> {

    private Condition<?, C> condition;

    private List<AbstractComponent<C>> thenComponent;

    private List<AbstractComponent<C>> elseComponent;

    public IfComponent(String name) {
        super(name);
    }

    @Override
    public void doExecute(C context) {
        Object test = condition.exec(context);;
        if(test == null){
            Invoker.invoke(context, elseComponent);
        }
        if(!(test instanceof Boolean)){
            throw new RuntimeException(String.format("[%s] condition operator must return boolean value，but return [%s]", getName(), test.getClass().getName()));
        }
        if(test == Boolean.TRUE){
            Invoker.invoke(context, thenComponent);
        }else{
            Invoker.invoke(context, elseComponent);
        }
    }
}
