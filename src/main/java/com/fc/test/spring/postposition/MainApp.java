package com.fc.test.spring.postposition;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName: MainApp
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/23 下午6:28
 **/
public class MainApp {

    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("/beans.xml");
        /*HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
        obj.getMessage();*/
        context.registerShutdownHook();
    }
}
