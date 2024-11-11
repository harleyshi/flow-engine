package com.flow.engine.node.definition;


import com.flow.engine.common.enums.NodeType;

/**
 * @author harley.shi
 * @date 2024/10/28
 */
public interface NodeDefinition extends Validator {

    String name();

    NodeType nodeType();
}
