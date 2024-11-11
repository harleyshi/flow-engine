package com.flow.engine.node.builder;

import com.flow.engine.operator.Condition;
import com.flow.engine.operator.Operator;
import com.flow.engine.node.AbstractComponent;
import com.flow.engine.node.IfComponent;
import com.flow.engine.node.PipelineComponent;
import com.flow.engine.model.FlowCtx;
import com.flow.engine.utils.AssertUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * if条件构造器
 * @author harley.shi
 * @date 2024/7/1
 */
public class IfBuilder<C extends FlowCtx> implements Builder<C> {

    private final String name;
    private Condition<?, C> condition;
    private List<AbstractComponent<C>> then = new ArrayList<>();
    private List<AbstractComponent<C>> otherwise = new ArrayList<>();

    public IfBuilder(String name) {
        this.name = name;
    }

    public IfBuilder<C> test(Condition<?, C> condition) {
        AssertUtil.notNull(condition, "condition must not be null!");
        this.condition = condition;
        return this;
    }

    public IfBuilder<C> then(AbstractComponent<C> component) {
        AssertUtil.notNull(condition, "You must invoke test method first!");
        this.then.add(component);
        return this;
    }

    public IfBuilder<C> then(PipelineComponent<C> component) {
        AssertUtil.notNull(condition, "You must invoke test method first!");
        AssertUtil.notEmpty(component.getPipeline(), "component pipeline must not be null!");
        this.then.add(component);
        return this;
    }

    public IfBuilder<C> then(List<AbstractComponent<C>> components) {
        AssertUtil.notNull(condition, "You must invoke test method first!");
        AssertUtil.notEmpty(components, "components must not be null!");
        this.then = components;
        return this;
    }

    public IfBuilder<C> otherwise(AbstractComponent<C> component) {
        AssertUtil.notNull(condition, "You must invoke test method first!");
        this.otherwise.add(component);
        return this;
    }

    public IfBuilder<C> otherwise(PipelineComponent<C> component) {
        AssertUtil.notNull(condition, "You must invoke test method first!");
        this.otherwise.add(component);
        return this;
    }

    public IfBuilder<C> otherwise(List<AbstractComponent<C>> components) {
        AssertUtil.notNull(condition, "You must invoke test method first!");
        AssertUtil.notEmpty(components, "components must not be null!");
        this.otherwise = components;
        return this;
    }

    @Override
    public IfComponent<C> build() {
        AssertUtil.notNull(condition, "condition must not be null");
        AssertUtil.notNull(then, "then branch must not be null");

        IfComponent<C> ifComponent = new IfComponent<>(name);
        ifComponent.setCondition(condition);
        ifComponent.setThenComponent(then);
        ifComponent.setElseComponent(otherwise);
        return ifComponent;
    }
}
