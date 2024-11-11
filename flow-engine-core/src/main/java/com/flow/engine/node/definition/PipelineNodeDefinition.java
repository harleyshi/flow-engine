package com.flow.engine.node.definition;

import com.flow.engine.common.enums.NodeType;
import com.flow.engine.utils.AssertUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
/**
 * @author harley.shi
 * @date 2024/10/28
 */
@Data
public class PipelineNodeDefinition implements NodeDefinition {

    /**
     * 组件名称
     */
    private String name;

    /**
     * 组件描述
     */
    private String desc;

    /**
     * 是否异步
     */
    private boolean async;

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * 是否忽略异常
     */
    private boolean ignoreException = false;

    /**
     * 算子列表
     */
    private List<NodeDefinition> children = new ArrayList<>();

    public void addChild(NodeDefinition child) {
        children.add(child);
    }

    @Override
    public void validate() {
        AssertUtil.notBlank(name, String.format("%s pipeline [name] cannot be blank", name));
        children.forEach(NodeDefinition::validate);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public NodeType nodeType() {
        return NodeType.PIPELINE;
    }
}
