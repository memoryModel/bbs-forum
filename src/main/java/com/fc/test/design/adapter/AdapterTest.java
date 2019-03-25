package com.fc.test.design.adapter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @ClassName: AdapterTest
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/12 下午4:20
 **/
public class AdapterTest implements Ps2 {

    private Usb usb;

    public AdapterTest(Usb usb) {
        this.usb = usb;
    }


    @Override
    public void isPs2() {
        usb.isUsb();
    }
}
