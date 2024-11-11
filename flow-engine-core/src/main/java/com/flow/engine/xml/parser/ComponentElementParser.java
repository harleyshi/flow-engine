package com.flow.engine.xml.parser;

import com.flow.engine.xml.ParserCtx;
import com.flow.engine.node.definition.NodeDefinition;
import com.flow.engine.node.definition.ComponentNodeDefinition;
import org.dom4j.Element;

import static com.flow.engine.common.constants.NodeAttrConstants.*;

/**
 * @author harley.shi
 * @date 2024/10/29
 */
public class ComponentElementParser extends ElementParser{

    @Override
    public NodeDefinition doParse(ParserCtx ctx, Element element) {
        ComponentNodeDefinition component = new ComponentNodeDefinition();
        // 正常流程算子
        String operator = element.attributeValue(OPERATOR);
        // 正常算子参数
        String operatorParamsKey = element.attributeValue(PARAMS);

        // 用于回滚的算子信息
        String rollback = element.attributeValue(ROLLBACK);
        String rollbackParamsKey = element.attributeValue(ROLLBACK_PARAMS);

        component.setOperator(operator);
        component.setParams(operatorParamsKey);
        component.setRollback(rollback);
        component.setRollbackParams(rollbackParamsKey);
        if (element.attributeValue(DESC) != null) {
            component.setDesc(element.attributeValue(DESC));
        }
        if (element.attributeValue(IGNORE_EXCEPTION) != null) {
            component.setIgnoreException(Boolean.parseBoolean(element.attributeValue(IGNORE_EXCEPTION)));
        }
        if (element.attributeValue(TIMEOUT) != null) {
            component.setTimeout(Integer.parseInt(element.attributeValue(TIMEOUT)));
        }
        return component;
    }
}
