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

import java.util.Map;

/**
 * @author harley.shi
 * @date 2024/11/4
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/engine")
public class EngineController {

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
        return R.ok(flowEngineService.detail(id));
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
