package com.ebki.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "jwt")
@Service
@Data @Setter @Getter
public class JwtConfig {

    private String securityKey;
    private String tokenPrefix;
    private int expirationAfterDays;
}
