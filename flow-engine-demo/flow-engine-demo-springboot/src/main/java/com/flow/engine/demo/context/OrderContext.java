package com.flow.engine.demo.context;

import com.flow.engine.model.AbstractFlowCtx;
import lombok.Data;
import lombok.ToString;

/**
 * @author harley.shi
 * @date 2024/7/2
 */
@Data
@ToString
public class OrderContext extends AbstractFlowCtx {
    private String orderId;

    private String orderName;

    private String orderDesc;

    private String orderStatus;
}
