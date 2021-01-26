package io.c12.bala.web.leaf.config;

import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

/**
 * Add below configuration to enable cache buster to application.yaml file
 * spring:
 *   web:
 *     resources:
 *       chain:
 *         enabled: true
 *         strategy:
 *           content:
 *             enabled: true
 *             paths: /js/**,/css/**,/icon/**
 *       cache:
 *         cachecontrol:
 *           max-age: 365d
 */
@Configuration
public class CacheBusterMVCConfig implements WebMvcConfigurer {

    @Bean
    @ConditionalOnEnabledResourceChain
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }
}
