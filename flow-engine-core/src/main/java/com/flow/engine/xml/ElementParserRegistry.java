package com.flow.engine.xml;

import com.flow.engine.common.enums.NodeType;
import com.flow.engine.xml.parser.*;


import java.util.HashMap;
import java.util.Map;

/**
 * @author harley.shi
 * @date 2024/10/28
 */
public class ElementParserRegistry {

    private static final ElementParserRegistry INSTANCE = new ElementParserRegistry();

    private final Map<String, ElementParser> MAPPINGS = new HashMap<>(32);

    private ElementParserRegistry() {
        this.register(NodeType.COMPONENT.getCode(), new ComponentElementParser());
        this.register(NodeType.IF.getCode(), new IfElementParser());
        this.register(NodeType.PIPELINE.getCode(), new PipelineElementParser());
        this.register(NodeType.CHOOSE.getCode(), new ChooseElementParser());
    }

    public static ElementParserRegistry getInstance() {
        return INSTANCE;
    }

    public ElementParser getParser(String elementName) {
        return MAPPINGS.get(elementName);
    }

    public void register(String elementName, ElementParser parser) {
        this.MAPPINGS.put(elementName, parser);
    }
}
