package com.flow.engine.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class FlowDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowDemoApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeFlowEngine(ApplicationReadyEvent event) {
        System.out.println("服务启动成功...........");
    }
}