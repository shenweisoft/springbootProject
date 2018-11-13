package com.jy.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置类，增加自定义拦截器和解析器
 * @author shenwei
 */
@Configuration
public class FilterConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(authorizationInterceptor);
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
