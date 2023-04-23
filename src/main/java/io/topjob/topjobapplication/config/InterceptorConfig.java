package io.topjob.topjobapplication.config;

import io.topjob.topjobapplication.util.ApiKeyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyInterceptor());
    }

    @Bean
    public ApiKeyInterceptor apiKeyInterceptor() {
        return new ApiKeyInterceptor();
    }

}
