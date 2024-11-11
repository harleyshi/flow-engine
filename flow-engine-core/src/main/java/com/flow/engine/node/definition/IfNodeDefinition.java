package com.flow.engine.node.definition;

import cn.hutool.core.collection.CollectionUtil;
import com.flow.engine.common.enums.NodeType;
import com.flow.engine.utils.AssertUtil;
import lombok.Data;

import java.util.List;

/**
 * @author harley.shi
 * @date 2024/10/28
 */
@Data
public class IfNodeDefinition extends ConditionNodeDefinition {

    private List<NodeDefinition> ifThen;

    private List<NodeDefinition> ifElse;

    @Override
    public void validate() {
        AssertUtil.notNull(test, String.format("%s if [test] cannot be null", test));
        AssertUtil.notEmpty(ifThen, String.format("%s [ifThen] cannot be empty", test));
        ifThen.forEach(Validator::validate);
        if(CollectionUtil.isNotEmpty(ifElse)){
            ifElse.forEach(Validator::validate);
        }
    }

    @Override
    public String name() {
        return test;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.IF;
    }
}
