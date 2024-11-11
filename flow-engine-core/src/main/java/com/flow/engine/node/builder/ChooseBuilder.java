package com.flow.engine.node.builder;


import com.flow.engine.operator.Condition;
import com.flow.engine.operator.Operator;
import com.flow.engine.node.ChooseComponent;
import com.flow.engine.node.AbstractComponent;
import com.flow.engine.model.FlowCtx;
import com.flow.engine.utils.AssertUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * choose构造器
 * @author harley.shi
 * @date 2024/7/1
 */
public class ChooseBuilder<C extends FlowCtx> implements Builder<C> {

    private final String name;
    private Condition<?, C> condition;
    private final Map<Object, List<AbstractComponent<C>>> branches = new ConcurrentHashMap<>();
    private List<AbstractComponent<C>> defaultBranch = new ArrayList<>();

    public ChooseBuilder(String name) {
        this.name = name;
    }

    void addBranch(Object when, AbstractComponent<C> branch) {
        List<AbstractComponent<C>> brancheList = branches.computeIfAbsent(when, k -> new ArrayList<>());
        brancheList.add(branch);
    }

    public ChooseBuilder<C> test(Condition<?, C> condition) {
        AssertUtil.notNull(condition, "condition must not be null!");
        this.condition = condition;
        return this;
    }

    /**
     * Create a new branch in choose.
     */
    public CaseBuilder<C> when(Object branch) {
        AssertUtil.notNull(condition, "You must invoke test method first!");
        AssertUtil.notNull(branch, "branch must not be null");
        AssertUtil.isFalse(branches.containsKey(branch), "duplicated branch");
        return new CaseBuilder<>(this, branch);
    }

    public ChooseBuilder<C> defaultBranch(AbstractComponent<C> branch) {
        AssertUtil.notNull(branch, "You must invoke test method first!");
        this.defaultBranch.add(branch);
        return this;
    }

    public ChooseBuilder<C> defaultBranch(List<AbstractComponent<C>> branchList) {
        AssertUtil.notEmpty(branchList, "You must invoke test method first!");
        this.defaultBranch = branchList;
        return this;
    }

    @Override
    public ChooseComponent<C> build() {
        AssertUtil.notNull(condition, "condition must not be null!");
        AssertUtil.isFalse(branches.isEmpty(), "choose builder has empty branch!");

        ChooseComponent<C> chooseComponent = new ChooseComponent<>(name);
        chooseComponent.setCondition(condition);
        chooseComponent.setBranches(branches);
        chooseComponent.setDefaultBranch(defaultBranch);
        return chooseComponent;
    }
}
