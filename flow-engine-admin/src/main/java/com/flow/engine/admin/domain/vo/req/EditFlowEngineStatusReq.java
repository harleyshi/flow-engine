package com.flow.engine.admin.domain.vo.req;

import lombok.Data;

import java.util.Date;

/**
 * @author harley.shi
 * @date 2024/10/30
 */
@Data
public class EditFlowEngineStatusReq {
    private Long id;

    private String status;
}
