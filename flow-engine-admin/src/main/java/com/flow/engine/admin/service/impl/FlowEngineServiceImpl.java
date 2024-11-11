package com.flow.engine.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flow.engine.admin.domain.entity.FlowEngineDO;
import com.flow.engine.admin.domain.vo.base.PageResp;
import com.flow.engine.admin.domain.vo.req.EditFlowEngineReq;
import com.flow.engine.admin.domain.vo.req.EditFlowEngineStatusReq;
import com.flow.engine.admin.domain.vo.req.QueryFlowEngineReq;
import com.flow.engine.admin.domain.vo.resp.QueryFlowEngineResp;
import com.flow.engine.admin.mapper.FlowEngineMapper;
import com.flow.engine.admin.service.FlowEngineService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
 *
 * @author harley.shi
 * @date 2024-09-26 11:27:36
 */
@Slf4j
@Service
public class FlowEngineServiceImpl extends ServiceImpl<FlowEngineMapper, FlowEngineDO> implements FlowEngineService {

    @Override
    public PageResp<QueryFlowEngineResp> page(QueryFlowEngineReq req) {
        Page<FlowEngineDO> pageRet = baseMapper.selectPage(req.buildPage(), buildQueryWrapper(req));
        return new PageResp<>(BeanUtil.copyToList(pageRet.getRecords(), QueryFlowEngineResp.class), pageRet.getTotal());
    }

    private LambdaQueryWrapper<FlowEngineDO> buildQueryWrapper(QueryFlowEngineReq req) {
        LambdaQueryWrapper<FlowEngineDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(req.getName()), FlowEngineDO::getName, req.getName());
        queryWrapper.orderByDesc(FlowEngineDO::getCreateTime);
        return queryWrapper;
    }

    @Override
    public QueryFlowEngineResp detail(Long id) {
        FlowEngineDO engineDO = baseMapper.selectById(id);
        if(engineDO == null){
            return null;
        }
        return BeanUtil.copyProperties(engineDO, QueryFlowEngineResp.class);
    }

    @Override
    public void insertOrUpdate(EditFlowEngineReq req) {
        // 新增
        if(req.getId() == null){
            FlowEngineDO engineDO = BeanUtil.copyProperties(req, FlowEngineDO.class);
            baseMapper.insert(engineDO);
        }else{// 更新
            FlowEngineDO engineDO = BeanUtil.copyProperties(req, FlowEngineDO.class);
            baseMapper.updateById(engineDO);
        }
    }

    @Override
    public void changeStatus(EditFlowEngineStatusReq req) {
        FlowEngineDO engineDO = new FlowEngineDO();
        engineDO.setId(req.getId());
        engineDO.setStatus(req.getStatus());
        baseMapper.updateById(engineDO);
    }
}