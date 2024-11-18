package com.flow.engine.admin.domain.vo.req;

import com.flow.engine.admin.domain.vo.base.PageReq;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author harley.shi
 * @date 2024-07-08 10:32:25
 */
@Data
public class QueryOperatorsReq extends PageReq implements Serializable{

    private String name;
}
