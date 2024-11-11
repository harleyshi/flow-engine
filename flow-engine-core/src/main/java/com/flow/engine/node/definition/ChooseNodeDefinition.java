package com.flow.engine.node.definition;

import cn.hutool.core.collection.CollectionUtil;
import com.flow.engine.common.enums.NodeType;
import com.flow.engine.utils.AssertUtil;
import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * @author harley.shi
 * @date 2024/10/28
 */
@Data
public class ChooseNodeDefinition extends ConditionNodeDefinition {

    /**
     * 默认分支
     */
    private List<NodeDefinition> defaultDef;

    /**
     * 条件分支
     */
    private Map<String, List<NodeDefinition>> caseMap;

    @Override
    public void validate() {
        AssertUtil.notBlank(test, String.format("%s choose [test] cannot be blank", test));
        AssertUtil.notBlank(test, String.format("%s choose [caseMap] cannot be empty", test));
        this.caseMap.values().forEach(value -> value.forEach(Validator::validate));
        if(CollectionUtil.isNotEmpty(defaultDef)){
            this.defaultDef.forEach(Validator::validate);
        }
    }

    @Override
    public String name() {
        return test;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.CHOOSE;
    }
}
