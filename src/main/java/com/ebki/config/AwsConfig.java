package com.ebki.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cloud.aws.credentials")
@Component
@Getter @Setter
public class AwsConfig {
    private String accessKey;
    private String secreteKey;
    private String region;
}
