package com.jsh.erp.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsFilterConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        // 1. 创建CORS配置
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 允许凭证
        config.addAllowedOrigin("http://127.0.0.1:8081"); // 指定具体源 ✅ 解决通配符冲突[8](@ref)
        config.addAllowedHeader("*"); // 允许所有头
        config.addAllowedMethod("*"); // 允许所有方法
        config.setMaxAge(3600L); // 预检请求缓存时间

        // 2. 注册CORS配置
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        // 3. 通过FilterRegistrationBean包装并设置最高优先级 ✅ 解决执行顺序问题[9,10](@ref)
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE); // 关键！确保最先执行

        return bean;
    }
}