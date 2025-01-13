package com.flow.engine.extension.springboot.reader;

import com.alibaba.fastjson2.JSONObject;
import com.flow.engine.FlowReader;
import com.flow.engine.common.enums.StatusEnum;
import com.flow.engine.model.FlowDSL;
import com.flow.engine.parser.DSLParser;
import java.util.List;

/**
 * db读取器
 * @author harley.shi
 * @date 2024/7/2
 */
public class DbEngineFlowReader implements FlowReader {

    private final DSLParser dslParser = new DSLParser();

    @Override
    public void load() {
        List<FlowDSL> flowList = getEngineList();
        for (FlowDSL flowDSL : flowList) {
            dslParser.parse(flowDSL);
        }
    }

    public List<FlowDSL> getEngineList(){
        FlowDSL flowDSL = new FlowDSL();
        flowDSL.setId(1L);
        flowDSL.setName("createOrder");
        flowDSL.setDescription("测试的一个流程引擎");
        flowDSL.setStatus(StatusEnum.PUBLISHED);
        FlowDSL.Graph graph = parseFlowGraph();
        flowDSL.setContent(graph);
        return List.of(flowDSL);
    }

    private static FlowDSL.Graph parseFlowGraph() {
        return JSONObject.parseObject(parallelContent, FlowDSL.Graph.class);
    }

    // 并行的流程
    static String parallelContent  = """

       {"nodes":[{"id":"1","type":"standard","initialized":false,"position":{"x":295,"y":136},"data":{},"label":"queryUserAddress","isScript":false,"script":null,"config":{"params":null,"rollbackParams":null,"timeout":536,"ignoreException":false,"async":false}},{"id":"2","type":"standard","initialized":false,"position":{"x":438.5,"y":111},"data":{},"label":"releaseStock","isScript":false,"script":null,"config":{"params":null,"rollbackParams":null,"timeout":183,"ignoreException":false,"async":false}},{"id":"3","type":"standard","initialized":false,"position":{"x":570,"y":114},"data":{},"label":"sendDelayMq","isScript":false,"script":null,"config":{"params":null,"rollbackParams":null,"timeout":809,"ignoreException":false,"async":false}},{"id":"4","type":"standard","initialized":false,"position":{"x":448.5,"y":217.5},"data":{},"label":"builderOrderInfo","isScript":false,"script":null,"config":{"params":null,"rollbackParams":null,"timeout":930,"ignoreException":false,"async":false}},{"id":"5","type":"standard","initialized":false,"position":{"x":449.5,"y":309.375},"data":{},"label":"saveOrderInfo","isScript":false,"script":null,"config":{"params":null,"rollbackParams":null,"timeout":134,"ignoreException":false,"async":false}}],"edges":[{"id":"vueflow__edge-1bottom-1-4top-4","type":"default","source":"1","target":"4","sourceHandle":"bottom-1","targetHandle":"top-4","data":{},"label":"","markerEnd":"arrowclosed","sourceX":346,"sourceY":171.5,"targetX":499.5,"targetY":214},{"id":"vueflow__edge-2bottom-2-4top-4","type":"default","source":"2","target":"4","sourceHandle":"bottom-2","targetHandle":"top-4","data":{},"label":"","markerEnd":"arrowclosed","sourceX":489.5,"sourceY":146.5,"targetX":499.5,"targetY":214},{"id":"vueflow__edge-3bottom-3-4top-4","type":"default","source":"3","target":"4","sourceHandle":"bottom-3","targetHandle":"top-4","data":{},"label":"","markerEnd":"arrowclosed","sourceX":621,"sourceY":149.5,"targetX":499.5,"targetY":214},{"id":"vueflow__edge-4bottom-4-5top-5","type":"default","source":"4","target":"5","sourceHandle":"bottom-4","targetHandle":"top-5","data":{},"label":"","markerEnd":"arrowclosed","sourceX":499.5,"sourceY":253,"targetX":500.5,"targetY":305.875}],"position":[-262,-102.375],"zoom":2,"viewport":{"x":-262,"y":-102.375,"zoom":2}}


    """;
}