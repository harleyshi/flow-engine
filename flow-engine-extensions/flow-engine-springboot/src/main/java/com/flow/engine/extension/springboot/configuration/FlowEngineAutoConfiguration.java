package com.flow.engine.extension.springboot.configuration;

import com.flow.engine.FlowReader;
import com.flow.engine.extension.springboot.properties.FlowEngineProperties;
import com.flow.engine.extension.springboot.reader.ClassPathXmlFlowReader;
import com.flow.engine.extension.springboot.SpringOperatorsInitializer;
import com.flow.engine.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author harley.shi
 * @date 2024/7/4
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({FlowEngineProperties.class})
public class FlowEngineAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public FlowReader flowReader(FlowEngineProperties properties) {
        AssertUtil.notNull(properties.getLocationPath(), "locationPath must not be null");
        return new ClassPathXmlFlowReader(properties.getLocationPath());
    }

    @Bean
    public SpringOperatorsInitializer operatorsInitializer(FlowReader flowReader) {
        return new SpringOperatorsInitializer(flowReader);
    }
}