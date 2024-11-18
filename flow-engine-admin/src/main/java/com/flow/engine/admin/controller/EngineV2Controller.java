package com.flow.engine.admin.controller;

import com.alibaba.fastjson2.JSON;
import com.flow.engine.admin.common.R;
import com.flow.engine.admin.domain.vo.base.PageResp;
import com.flow.engine.admin.domain.vo.req.EditFlowEngineReq;
import com.flow.engine.admin.domain.vo.req.EditFlowEngineStatusReq;
import com.flow.engine.admin.domain.vo.req.QueryFlowEngineReq;
import com.flow.engine.admin.domain.vo.resp.QueryFlowEngineResp;
import com.flow.engine.admin.service.FlowEngineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author harley.shi
 * @date 2024/11/4
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/engine")
public class EngineV2Controller {

    @Autowired
    private FlowEngineService flowEngineService;

    @GetMapping("/list")
    public R<PageResp<QueryFlowEngineResp>> list(QueryFlowEngineReq req) {
        log.info("EngineController list req: {}", JSON.toJSONString(req));
        return R.ok(flowEngineService.page(req));
    }

    @GetMapping("/detail")
    public R<QueryFlowEngineResp> detail(Long id) {
        log.info("EngineController detail req: {}", id);
        QueryFlowEngineResp resp = flowEngineService.detail(id);
        resp.setContent("{\"nodes\":[{\"id\":\"1\",\"type\":\"business\",\"initialized\":false,\"position\":{\"x\":295.96875,\"y\":159},\"data\":{},\"label\":\"业务节点31\",\"config\":{\"timeout\":0,\"ignoreException\":false,\"async\":false}},{\"id\":\"2\",\"type\":\"business\",\"initialized\":false,\"position\":{\"x\":293.953125,\"y\":275},\"data\":{},\"label\":\"业务节点81\",\"config\":{\"timeout\":0,\"ignoreException\":false,\"async\":false}},{\"id\":\"3\",\"type\":\"business\",\"initialized\":false,\"position\":{\"x\":289.98455813302076,\"y\":380.1414490985936},\"data\":{},\"label\":\"业务节点51\",\"config\":{\"timeout\":0,\"ignoreException\":false,\"async\":false}},{\"id\":\"4\",\"type\":\"business\",\"initialized\":false,\"position\":{\"x\":266.7336260312778,\"y\":491.98668059939894},\"data\":{},\"label\":\"业务节点81\",\"config\":{\"timeout\":0,\"ignoreException\":false,\"async\":false}}],\"edges\":[{\"id\":\"vueflow__edge-1bottom-1-2top-2\",\"type\":\"default\",\"source\":\"1\",\"target\":\"2\",\"sourceHandle\":\"bottom-1\",\"targetHandle\":\"top-2\",\"data\":{},\"label\":\"\",\"sourceX\":331.96875,\"sourceY\":188.5,\"targetX\":329.953125,\"targetY\":271.5},{\"id\":\"vueflow__edge-2bottom-2-3top-3\",\"type\":\"default\",\"source\":\"2\",\"target\":\"3\",\"sourceHandle\":\"bottom-2\",\"targetHandle\":\"top-3\",\"data\":{},\"label\":\"\",\"sourceX\":329.953125,\"sourceY\":304.5,\"targetX\":325.98455813302076,\"targetY\":376.6414490985936},{\"id\":\"vueflow__edge-3bottom-3-4top-4\",\"type\":\"default\",\"source\":\"3\",\"target\":\"4\",\"sourceHandle\":\"bottom-3\",\"targetHandle\":\"top-4\",\"data\":{},\"label\":\"\",\"sourceX\":325.98455813302076,\"sourceY\":409.6414490985936,\"targetX\":302.73359435522,\"targetY\":488.486679740889}],\"position\":[-72.9883796174098,-187.18031704580403],\"zoom\":1.3195079107728942,\"viewport\":{\"x\":-72.9883796174098,\"y\":-187.18031704580403,\"zoom\":1.3195079107728942}}");
        return R.ok();
    }

    @GetMapping("/changeStatus")
    public R<Void> changeStatus(EditFlowEngineStatusReq req) {
        log.info("EngineController changeStatus req: {}", JSON.toJSONString(req));
        flowEngineService.changeStatus(req);
        return R.ok();
    }

    @PostMapping("/edit")
    public R<Void> edit(@RequestBody EditFlowEngineReq req) {
        log.info("EngineController edit req: {}", JSON.toJSONString(req));
        flowEngineService.insertOrUpdate(req);
        return R.ok();
    }
}
