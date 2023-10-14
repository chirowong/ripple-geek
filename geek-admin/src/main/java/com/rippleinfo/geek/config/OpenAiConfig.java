package com.rippleinfo.geek.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "openai")
public class OpenAiConfig {
    private String chatUrl;

    private String fileUrl;

    private String model;
}
