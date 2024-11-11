package com.flow.engine.extension.springboot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author harley.shi
 * @date 2024/11/4
 */
@Data
@ConfigurationProperties(prefix = "flow.engine")
public class FlowEngineProperties {

    private String locationPath;
}
