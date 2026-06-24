package com.brokerfinder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration that registers interceptors.
 * Protects admin routes and broker dashboard routes.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AdminInterceptor adminInterceptor;
    private final BrokerInterceptor brokerInterceptor;

    public WebConfig(AdminInterceptor adminInterceptor, BrokerInterceptor brokerInterceptor) {
        this.adminInterceptor = adminInterceptor;
        this.brokerInterceptor = brokerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Protect admin routes
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login");

        // Protect broker dashboard routes
        registry.addInterceptor(brokerInterceptor)
                .addPathPatterns("/broker/dashboard", "/broker/accept/**", "/broker/reject/**");
    }
}
