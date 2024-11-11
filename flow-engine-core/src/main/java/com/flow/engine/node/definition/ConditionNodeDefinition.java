package com.flow.engine.node.definition;

import lombok.Data;

/**
 * @author harley.shi
 * @date 2024/10/28
 */
@Data
public abstract class ConditionNodeDefinition implements NodeDefinition {

    /**
     * 条件表达式
     */
    protected String test;

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * 是否忽略异常
     */
    private boolean ignoreException = false;
}
