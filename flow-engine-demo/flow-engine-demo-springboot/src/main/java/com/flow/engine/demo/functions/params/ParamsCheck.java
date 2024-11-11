package com.flow.engine.demo.functions.params;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author harley.shi
 * @date 2024/7/3
 */
@Data
public class ParamsCheck {
    private String orderId;
    private Boolean orderFromShoppingCart;
    private BigDecimal orderAmount;
}
