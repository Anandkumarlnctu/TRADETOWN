package com.PACKAGE.TRADETOWN.ECOMM.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Make sure that static resources are properly handled
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        
        // Ensure that API endpoints are not treated as static resources
        registry.addResourceHandler("/cart/**")
                .addResourceLocations("classpath:/META-INF/resources/");
    }
}
