package com.flow.engine.admin.controller;

import com.alibaba.fastjson2.JSON;
import com.flow.engine.admin.common.OpsNodeType;
import com.flow.engine.admin.common.R;
import com.flow.engine.admin.domain.vo.base.PageResp;
import com.flow.engine.admin.domain.vo.req.QueryOperatorsReq;
import com.flow.engine.admin.domain.vo.resp.OperatorsResp;
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
    public R<PageResp<OperatorsResp>> list(QueryOperatorsReq req) {
        log.info("OperatorsController list req: {}", JSON.toJSONString(req));
        List<OperatorsResp> list = new ArrayList<>();
        for (int i = 0; i < req.getPageSize(); i++) {
            OperatorsResp resp = new OperatorsResp();
            if(i%2==0){
                resp.setType(OpsNodeType.STANDARD.getCode());
            }else{
                resp.setType(OpsNodeType.CONDITION.getCode());
                if(i%3==0){
                    resp.setIsScript(true);
                }
            }
            resp.setLabel(resp.getType()+"：节点-"+new Random().nextInt(10)+1);
            OperatorsResp.AdvancedConfig advancedConfig = new OperatorsResp.AdvancedConfig();
            advancedConfig.setTimeout(new Random().nextInt(1000)+1);
            resp.setConfig(advancedConfig);
            resp.setVersion(new Random().nextInt(1000)+1+"");
            list.add(resp);
        }
        PageResp<OperatorsResp> pageResp = new PageResp<>();
        pageResp.setTotal(100L);
        pageResp.setRecords(list);
        return R.ok(pageResp);
    }
}
