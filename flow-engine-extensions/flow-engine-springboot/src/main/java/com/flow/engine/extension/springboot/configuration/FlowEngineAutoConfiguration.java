package com.flow.engine.extension.springboot.configuration;

import com.flow.engine.FlowReader;
import com.flow.engine.extension.springboot.SpringOperatorsInitializer;
import com.flow.engine.extension.springboot.reader.DbEngineFlowReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author harley.shi
 * @date 2024/7/4
 */
@Slf4j
@Configuration
public class FlowEngineAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public FlowReader flowReader() {
        return new DbEngineFlowReader();
    }

    @Bean
    public SpringOperatorsInitializer operatorsInitializer(FlowReader flowReader) {
        return new SpringOperatorsInitializer(flowReader);
    }
}