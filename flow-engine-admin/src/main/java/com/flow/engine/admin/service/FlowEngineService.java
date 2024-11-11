package com.flow.engine.admin.service;


import com.flow.engine.admin.domain.vo.base.PageResp;
import com.flow.engine.admin.domain.vo.req.EditFlowEngineReq;
import com.flow.engine.admin.domain.vo.req.EditFlowEngineStatusReq;
import com.flow.engine.admin.domain.vo.req.QueryFlowEngineReq;
import com.flow.engine.admin.domain.vo.resp.QueryFlowEngineResp;

/**
 *
 * @author harley.shi
 * @date 2024-09-26 13:44:47
 */
public interface FlowEngineService{

    PageResp<QueryFlowEngineResp> page(QueryFlowEngineReq req);

    QueryFlowEngineResp detail(Long id);

    void insertOrUpdate(EditFlowEngineReq req);

    void changeStatus(EditFlowEngineStatusReq req);
}