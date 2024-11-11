package com.flow.engine.model;

import com.flow.engine.FlowExecutor;
import com.flow.engine.common.enums.NodeType;
import com.flow.engine.node.definition.parser.*;
import com.flow.engine.operator.Condition;
import com.flow.engine.node.PipelineComponent;
import com.flow.engine.node.builder.Builders;
import com.flow.engine.node.builder.EngineBuilder;
import com.flow.engine.node.builder.ScriptBuilder;
import com.flow.engine.script.ScriptExecutor;
import com.flow.engine.node.definition.ParamsDefinition;
import com.flow.engine.node.definition.PipelineNodeDefinition;
import com.flow.engine.node.definition.ScriptDefinition;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author harley.shi
 * @date 2024/10/29
 */
@Getter
public class EngineModel<C extends FlowCtx> {
    private final String engineName;

    private final PipelineNodeDefinition pipeline;

    private final Map<String, Condition<?, C>> scriptMap = new HashMap<>();

    private final Map<String, ParamsDefinition> paramsMap = new HashMap<>();

    private final Map<NodeType, AbstractParser<C>> parserMap = new HashMap<>(32);

    public EngineModel(String engineName, PipelineNodeDefinition pipeline) {
        this.engineName = engineName;
        this.pipeline = pipeline;

        parserMap.put(NodeType.COMPONENT, new ComponentParser<>());
        parserMap.put(NodeType.IF, new IfComponentParser<>());
        parserMap.put(NodeType.PIPELINE, new PipelineComponentParser<>());
        parserMap.put(NodeType.CHOOSE, new ChooseComponentParser<>());
    }

    public void builderParamsList(List<ParamsDefinition> paramsList){
        paramsList.forEach(params -> paramsMap.put(params.getName(), params));
    }

    public void builderScript(List<ScriptDefinition> scriptList){
        scriptList.forEach(definition -> {
            ScriptBuilder<?, C> scriptBuilder = Builders.script();
            ScriptExecutor<?, C> scriptExecutor = scriptBuilder.name(definition.getName())
                    .script(definition.getScript())
                    .type(definition.getType())
                    .build();
            Condition<?, C> operator = scriptExecutor::execute;
            scriptMap.put(definition.getName(), operator);
        });
    }

    public String getParams(String name){
        ParamsDefinition params = paramsMap.get(name);
        return params == null ? null : params.getParams();
    }

    public Condition<?, C> getScript(String name){
        return scriptMap.get(name);
    }

    public AbstractParser<C> getComponentParser(NodeType nodeType){
        return parserMap.get(nodeType);
    }

    public FlowExecutor<C> build(){
        PipelineComponent<C> pipelineComponent = (PipelineComponent<C>) getComponentParser(NodeType.PIPELINE).doParse(this, pipeline);
        EngineBuilder<C> engineBuilder = Builders.engine();
        engineBuilder.name(engineName);
        engineBuilder.pipeline(pipelineComponent);
        return engineBuilder.build();
    }
}
