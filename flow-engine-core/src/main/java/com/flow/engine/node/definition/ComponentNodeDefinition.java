package com.flow.engine.node.definition;

import com.flow.engine.common.enums.NodeType;
import com.flow.engine.utils.AssertUtil;
import lombok.Data;

/**
 * @author harley.shi
 * @date 2024/10/28
 */
@Data
public class ComponentNodeDefinition implements NodeDefinition {

    /**
     * 组件描述
     */
    private String desc;

    /**
     * 算子
     */
    private String operator;

    /**
     * 算子参数
     */
    private String params;

    /**
     * 回滚算子
     */
    private String rollback;

    /**
     * 回滚算子参数
     */
    private String rollbackParams;

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * 是否忽略异常
     */
    private boolean ignoreException = false;

    @Override
    public void validate() {
        AssertUtil.notBlank(operator, String.format("%s component [operator] cannot be null", operator));
    }

    @Override
    public String name() {
        return operator;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.COMPONENT;
    }
}
