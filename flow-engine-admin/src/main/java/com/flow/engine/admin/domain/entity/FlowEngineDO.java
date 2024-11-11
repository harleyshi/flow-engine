package com.flow.engine.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author harley.shi
 * @date 2024/10/30
 */
@Getter
@Setter
@TableName("flow_engine")
public class FlowEngineDO {
    @TableId(value = "id")
    private Long id;

    private String name;

    private String description;

    private String content;

    private String status;

    private Date createTime;

    private Date updateTime;
}
