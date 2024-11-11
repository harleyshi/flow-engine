package com.flow.engine.xml;


import com.flow.engine.FlowExecutor;
import com.flow.engine.common.enums.NodeType;
import com.flow.engine.common.enums.ScriptLang;
import com.flow.engine.model.FlowCtx;
import com.flow.engine.node.definition.ParamsDefinition;
import com.flow.engine.node.definition.PipelineNodeDefinition;
import com.flow.engine.node.definition.ScriptDefinition;
import com.flow.engine.utils.AssertUtil;
import com.flow.engine.xml.parser.ElementParser;
import com.flow.engine.common.constants.NodeAttrConstants;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * xml流程解析器
 * @author harley.shi
 * @version 1.0
 */
public class XmlFlowParser implements FlowParser {

    private Document getDocument(String xmlContent) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        StringReader reader = new StringReader(xmlContent);
        return saxReader.read(reader);
    }

    @Override
    public <C extends FlowCtx> FlowExecutor<C> parse(String xmlContent) throws Exception {
        Document document = getDocument(xmlContent);
        Element rootElement = document.getRootElement();
        ParserCtx parserCtx = parserElementContext(rootElement);
        return parserCtx.buildEngine();
    }

    public ParserCtx parserElementContext(Element rootElement) {
        ParserCtx parserCtx = new ParserCtx();
        for (Element element : rootElement.elements()) {
            NodeType nodeType = NodeType.getByCode(element.getName());
            AssertUtil.notNull(nodeType, "未知的节点类型：" + element.getName());
            switch (nodeType) {
                case ENGINE_NAME:
                    parserCtx.setEngineName(element.attributeValue(NodeAttrConstants.NAME));
                    break;
                case CONFIG_PARAMS:
                    parserCtx.setParamsList(doParseParams(element));
                    break;
                case SCRIPT:
                    parserCtx.addScript(doParseScript(element));
                    break;
                case PIPELINE:
                    parserCtx.setPipeline(doParsePipeline(parserCtx, element));
                    break;
                default:
                    break;
            }
        }
        return parserCtx;
    }

    private List<ParamsDefinition> doParseParams(Element element) {
        List<Element> children = element.elements();
        List<ParamsDefinition> paramsList = new ArrayList<>();
        for (Element child : children) {
            String name = child.attributeValue(NodeAttrConstants.NAME);
            String desc = child.attributeValue(NodeAttrConstants.DESC);
            String params = child.getStringValue();
            ParamsDefinition paramsDefinition = new ParamsDefinition();
            paramsDefinition.setName(name);
            paramsDefinition.setDesc(desc);
            paramsDefinition.setParams(params);
            paramsList.add(paramsDefinition);
        }
        return paramsList;
    }

    private ScriptDefinition doParseScript(Element element) {
        String name = element.attributeValue(NodeAttrConstants.NAME);
        String type = element.attributeValue(NodeAttrConstants.SCRIPT_TYPE);
        String scriptValue = element.getStringValue();
        ScriptDefinition scriptDefinition = new  ScriptDefinition();
        scriptDefinition.setName(name);
        scriptDefinition.setType(StringUtils.isBlank(type) ? ScriptLang.GROOVY.getCode() : type);
        scriptDefinition.setScript(scriptValue);
        return scriptDefinition;
    }

    private PipelineNodeDefinition doParsePipeline(ParserCtx parserCtx, Element element) {
        ElementParser parser = parserCtx.getParser(NodeType.PIPELINE.getCode());
        return (PipelineNodeDefinition) parser.doParse(parserCtx, element);
    }
}
