package com.jy.utils;
import mypackage.DemoServiceImplService;

public class Test {

    public static void main(String[] args) {


        DemoServiceImplService webServiceImpl = new DemoServiceImplService();
        String result = webServiceImpl.getDemoServiceImplPort().sayHello("没有说");
        System.out.println("===========================================");
        System.out.println(result);
        System.out.println("===========================================");
    }
}
