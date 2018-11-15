package com.jy.service;

import javax.jws.WebService;

@WebService
public interface DemoWebService {

    public String sayHello(String user);
}
