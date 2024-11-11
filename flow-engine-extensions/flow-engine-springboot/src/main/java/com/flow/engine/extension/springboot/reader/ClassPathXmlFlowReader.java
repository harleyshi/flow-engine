package com.flow.engine.extension.springboot.reader;

import com.flow.engine.FlowReader;
import com.flow.engine.xml.EngineLoader;
import com.flow.engine.exception.FlowException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 本地classpath下xml配置文件读取器
 * @author harley.shi
 * @date 2024/7/2
 */

public class ClassPathXmlFlowReader implements FlowReader {

    private final String locationPath;

    private final EngineLoader engineLoader = new EngineLoader();

    public ClassPathXmlFlowReader(String locationPath) {
        this.locationPath = locationPath;
    }

    @Override
    public void load() {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources;
        try {
            resources = resolver.getResources(locationPath);
        } catch (Exception e) {
            throw new FlowException("资源读取失败", e);
        }
        for (Resource resource : resources) {
            try (InputStream inputStream = resource.getInputStream()){
                byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
                engineLoader.load(new String(bytes, StandardCharsets.UTF_8));
            }catch (Exception e){
                throw new FlowException(String.format("[%s] 文件内容解析错误", resource.getFilename()), e);
            }
        }
    }
}