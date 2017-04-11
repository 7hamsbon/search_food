package com.ham.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

/**
 * Created by hamsbon on 2017/2/14.
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

//    @Bean
//    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
//        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
//        Properties  properties = new Properties();
//        properties.put("java.lang.Throwable","error_500");
//        simpleMappingExceptionResolver.setExceptionMappings(properties);
//        return simpleMappingExceptionResolver;
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

//        registry.addInterceptor(new AuthInteceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("/");
        return factory.createMultipartConfig();
    }
}
