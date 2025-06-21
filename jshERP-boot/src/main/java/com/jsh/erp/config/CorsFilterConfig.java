package com.jsh.erp.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域过滤器
 */
@Configuration
public class CorsFilterConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        // 1. 创建 CORS 配置
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 允许携带凭证（如 Cookie）
        config.addAllowedOrigin("*"); // 2.0 版本暂不支持 allowedOriginPatterns，需显式指定域名或保留 *
        config.addAllowedHeader("*"); // 允许所有请求头
        config.addAllowedMethod("*"); // 允许所有 HTTP 方法
        config.setMaxAge(3600L); // 预检请求缓存时间

        // 2. 注册 CORS 配置到所有路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        // 3. 创建过滤器并设置优先级（关键！）
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CorsFilter(source));
        registration.setName("corsFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE); // 优先级设为最高[8](@ref)
        return registration;
    }
}

