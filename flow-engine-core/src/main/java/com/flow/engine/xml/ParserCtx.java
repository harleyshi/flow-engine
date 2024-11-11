package com.flow.engine.xml;

import com.flow.engine.FlowExecutor;
import com.flow.engine.model.EngineModel;
import com.flow.engine.model.FlowCtx;
import com.flow.engine.utils.AssertUtil;
import com.flow.engine.node.definition.ParamsDefinition;
import com.flow.engine.node.definition.PipelineNodeDefinition;
import com.flow.engine.node.definition.ScriptDefinition;
import com.flow.engine.xml.parser.ElementParser;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author harley.shi
 * @date 2024/10/28
 */
@Data
public class ParserCtx {
    private String engineName;

    private List<ParamsDefinition> paramsList;

    private final List<ScriptDefinition> scriptList = new ArrayList<>();

    private PipelineNodeDefinition pipeline;

    private final ElementParserRegistry parserRegistry = ElementParserRegistry.getInstance();

    public <C extends FlowCtx> FlowExecutor<C> buildEngine() {
        // 配置校验
        validate();

        EngineModel<C> engineModel = new EngineModel<>(engineName, pipeline);
        engineModel.builderParamsList(paramsList);
        engineModel.builderScript(scriptList);
        return engineModel.build();
    }

    public ElementParser getParser(String name) {
        return parserRegistry.getParser(name);
    }

    public void addScript(ScriptDefinition script) {
        scriptList.add(script);
    }

    private void validate() {
        AssertUtil.notBlank(engineName, "ParserCtx [engineName] cannot be blank");
        paramsList.forEach(ParamsDefinition::validate);
        scriptList.forEach(ScriptDefinition::validate);
        pipeline.validate();
    }
}
