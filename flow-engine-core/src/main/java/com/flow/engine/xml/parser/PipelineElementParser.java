package com.flow.engine.xml.parser;

import com.flow.engine.xml.ParserCtx;
import com.flow.engine.node.definition.NodeDefinition;
import com.flow.engine.node.definition.PipelineNodeDefinition;
import org.dom4j.Element;

import java.util.List;

import static com.flow.engine.common.constants.NodeAttrConstants.*;

/**
 * @author harley.shi
 * @date 2024/10/29
 */
public class PipelineElementParser extends ElementParser {

    @Override
    public NodeDefinition doParse(ParserCtx ctx, Element element) {
        PipelineNodeDefinition pipeline = new PipelineNodeDefinition();
        List<Element> children = element.elements();
        for (Element child : children) {
            pipeline.addChild(parseSubElement(ctx, child));
        }
        if (element.attributeValue(NAME) != null) {
            pipeline.setName(element.attributeValue(NAME));
        }
        if (element.attributeValue(ASYNC) != null) {
            pipeline.setAsync(Boolean.parseBoolean(element.attributeValue(ASYNC)));
        }
        if (element.attributeValue(DESC) != null) {
            pipeline.setDesc(element.attributeValue(DESC));
        }
        if (element.attributeValue(IGNORE_EXCEPTION) != null) {
            pipeline.setIgnoreException(Boolean.parseBoolean(element.attributeValue(IGNORE_EXCEPTION)));
        }
        if (element.attributeValue(TIMEOUT) != null) {
            pipeline.setTimeout(Integer.parseInt(element.attributeValue(TIMEOUT)));
        }
        return pipeline;
    }
}
