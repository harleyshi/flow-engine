package com.flow.engine.xml.parser;

import com.flow.engine.xml.ParserCtx;
import com.flow.engine.node.definition.NodeDefinition;
import com.flow.engine.node.definition.IfNodeDefinition;
import org.dom4j.Element;

import java.util.List;


import static com.flow.engine.common.constants.NodeAttrConstants.*;

/**
 * @author harley.shi
 * @date 2024/10/29
 */
public class IfElementParser extends ElementParser{

    @Override
    public NodeDefinition doParse(ParserCtx ctx, Element element) {
        IfNodeDefinition ifDefinition = new IfNodeDefinition();
        String test = element.attributeValue(TEST);
        ifDefinition.setTest(test);

        // 处理默认分支
        Element thenElement = element.element(THEN);
        List<Element> thenChildren = thenElement.elements();
        ifDefinition.setIfThen(parseSubElements(ctx, thenChildren));

        // 处理else分支
        Element elseElement = element.element(ELSE);
        if(elseElement != null){
            List<Element> elseChildren = elseElement.elements();
            ifDefinition.setIfElse(parseSubElements(ctx, elseChildren));
        }

        if (element.attributeValue(IGNORE_EXCEPTION) != null) {
            ifDefinition.setIgnoreException(Boolean.parseBoolean(element.attributeValue(IGNORE_EXCEPTION)));
        }
        if (element.attributeValue(TIMEOUT) != null) {
            ifDefinition.setTimeout(Integer.parseInt(element.attributeValue(TIMEOUT)));
        }
        return ifDefinition;
    }
}

