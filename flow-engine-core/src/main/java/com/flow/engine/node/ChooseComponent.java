package com.flow.engine.node;

import com.flow.engine.model.FlowCtx;
import com.flow.engine.operator.Condition;
import com.flow.engine.operator.Operator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * choose组件
 * @author harley.shi
 * @date 2024/7/1
 */
@Slf4j
@Getter
@Setter
public class ChooseComponent<C extends FlowCtx> extends AbstractComponent<C> {

    private Condition<?, C> condition;

    private Map<Object, List<AbstractComponent<C>>> branches;

    private List<AbstractComponent<C>> defaultBranch;

    public ChooseComponent(String name) {
        super(name);
    }

    @Override
    public void doExecute(C context) {
        Object branch = condition.exec(context);
        List<AbstractComponent<C>> components = null;
        if (branch != null && branches.containsKey(branch)) {
            components = branches.get(branch);
        } else if (defaultBranch != null) {
            components = defaultBranch;
        }
        if (components != null) {
            Invoker.invoke(context, components);
        }
    }
}
