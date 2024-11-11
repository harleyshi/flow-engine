package com.flow.engine.xml.parser;

import com.flow.engine.xml.ParserCtx;
import com.flow.engine.utils.AssertUtil;
import com.flow.engine.node.definition.NodeDefinition;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;


/**
 * 元素解析器
 * @author harley.shi
 * @date 2024/6/25
 */
public abstract class ElementParser {

    public abstract NodeDefinition doParse(ParserCtx ctx, Element element);

    protected NodeDefinition parseSubElement(ParserCtx context, Element sub) {
        String subName = sub.getName();
        ElementParser parser = context.getParser(subName);
        AssertUtil.notNull(parser, "Could not find parser for element " + subName);
        return parser.doParse(context, sub);
    }

    protected List<NodeDefinition> parseSubElements(ParserCtx ctx, List<Element> subList) {
        if(subList == null || subList.isEmpty()){
            return null;
        }
        List<NodeDefinition> definitions = new ArrayList<>();
        for (Element element : subList) {
            definitions.add(parseSubElement(ctx, element));
        }
        return definitions;
    }
}
