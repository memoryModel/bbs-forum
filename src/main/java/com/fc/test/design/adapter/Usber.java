package com.fc.test.design.adapter;

/**
 * @ClassName: Usber
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/12 下午6:37
 **/
public class Usber implements Usb {

    @Override
    public void isUsb() {
        System.out.println("USB口");
    }
}
