package com.fc.test.spring.postposition;

/**
 * @ClassName: HelloWorld
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/23 下午6:26
 **/
public class HelloWorldOne {

    private String message;

    public void getMessage() {
        System.out.println("Your Message : " + message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void init() {
        System.out.println("Bean is going through init.");
    }

    public void destroy() {
        System.out.println("Bean will destroy now.");
    }

}
