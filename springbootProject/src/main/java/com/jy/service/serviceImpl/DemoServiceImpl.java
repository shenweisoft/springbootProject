package com.jy.service.serviceImpl;

import com.jy.service.DemoWebService;

import java.util.Date;

public class DemoServiceImpl implements DemoWebService {

    @Override
    public String sayHello(String user) {
        return user+":hello"+"("+new Date()+")";
    }
}
