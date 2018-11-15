package com.jy.config.webservice;


import com.jy.service.DemoWebService;
import com.jy.service.serviceImpl.DemoServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServicePublishListener {

    //https://blog.csdn.net/hawako/article/details/80556676
    //评论中说的是public ServletRegistrationBean dispatcherServlet() 把默认映射覆盖掉了，把这个名字改掉，控制类方法就能访问了。
    //更改此方法明后可以正常其他请求url，webservice服务也正常。
    @Bean
    public ServletRegistrationBean disServlet(){

        return new ServletRegistrationBean(new CXFServlet() , "/services/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }
    @Bean
    public DemoWebService demoService() {
        return new DemoServiceImpl();
    }
    @Bean//发布多个就多写一个
    public Endpoint endpoint() {
        //http://localhost:8080/lawProject/services 访问地址列表
        //http://localhost:8080/lawProject/services/api?wsdl
        EndpointImpl endpoint = new EndpointImpl(springBus(), demoService());

        endpoint.publish("/api");
        return endpoint;
    }
    //webservice通过wsdl文件生成java代码方法 点击项目右键Webservices-->  Web Service Platform 选择：Glassfish /JAX - WS 2.2RI/Metro.....

}