package com.flow.engine.admin.controller;

import com.alibaba.fastjson2.JSON;
import com.flow.engine.admin.common.OpsNodeType;
import com.flow.engine.admin.common.R;
import com.flow.engine.admin.domain.vo.base.PageResp;
import com.flow.engine.admin.domain.vo.req.QueryOperatorsReq;
import com.flow.engine.admin.domain.vo.resp.QueryOperatorsResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author harley.shi
 * @date 2024/11/4
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/operators")
public class OperatorsController {

    @GetMapping("/list")
    public R<PageResp<QueryOperatorsResp>> list(QueryOperatorsReq req) {
        log.info("OperatorsController list req: {}", JSON.toJSONString(req));
        List<QueryOperatorsResp> list = new ArrayList<>();
        for (int i = 0; i < req.getPageSize(); i++) {
            QueryOperatorsResp resp = new QueryOperatorsResp();
            resp.setType(OpsNodeType.BUSINESS.getCode());
            resp.setLabel("业务节点"+new Random().nextInt(10)+1);
            QueryOperatorsResp.AdvancedConfig advancedConfig = new QueryOperatorsResp.AdvancedConfig();
            advancedConfig.setTimeout(new Random().nextInt(1000)+1);
            resp.setConfig(advancedConfig);
            list.add(resp);
        }
        PageResp<QueryOperatorsResp> pageResp = new PageResp<>();
        pageResp.setTotal(100L);
        pageResp.setRecords(list);
        return R.ok(pageResp);
    }
}
