package com.flow.engine.node.builder;

import com.flow.engine.node.AbstractComponent;
import com.flow.engine.node.PipelineComponent;
import com.flow.engine.model.FlowCtx;
import com.flow.engine.utils.AssertUtil;

import java.util.List;

/**
 * case构造器
 * @author harley.shi
 * @date 2024/7/1
 */
public class CaseBuilder<C extends FlowCtx> {

    /**
     * Case condition.
     */
    private final Object when;

    private final ChooseBuilder<C> chooseBuilder;

    CaseBuilder(ChooseBuilder<C> chooseBuilder, Object when) {
        this.when = when;
        this.chooseBuilder = chooseBuilder;
    }

    public ChooseBuilder<C> then(AbstractComponent<C> branch) {
        AssertUtil.notNull(branch, "branch must not be null");
        chooseBuilder.addBranch(when, branch);
        return chooseBuilder;
    }

    public ChooseBuilder<C> then(List<AbstractComponent<C>> branchList) {
        AssertUtil.notEmpty(branchList, "branchList must not be empty");
        for (AbstractComponent<C> component : branchList) {
            chooseBuilder.addBranch(when, component);
        }
        return chooseBuilder;
    }

    public ChooseBuilder<C> then(PipelineComponent<C> branch) {
        AssertUtil.notNull(branch, "branch must not be null");
        AssertUtil.notEmpty(branch.getPipeline(), "branch pipeline must not be empty");
        chooseBuilder.addBranch(when, branch);
        return chooseBuilder;
    }
}
