package com.flow.engine.admin.domain.vo.resp;

import lombok.Data;

import java.util.Date;

/**
 * @author harley.shi
 * @date 2024/10/30
 */
@Data
public class QueryFlowEngineResp {
    private String id;

    private String name;

    private String description;

    private String content;

    private String status;

    private Date updateTime;
}
