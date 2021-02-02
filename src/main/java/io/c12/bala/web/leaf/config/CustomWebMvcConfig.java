package io.c12.bala.web.leaf.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
@RequiredArgsConstructor
public class CustomWebMvcConfig implements WebMvcConfigurer {

    private final LoggerInterceptor loggerInterceptor;

    /**
     * Cache Buster configuration.
     * @return resource url encoder
     */
    @Bean
    @ConditionalOnEnabledResourceChain
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }

    /**
     * add Logger interceptor to Web MVC.
     * @param registry to add logger interceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor);
    }
}
