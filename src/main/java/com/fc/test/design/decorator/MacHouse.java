package com.fc.test.design.decorator;

/**
 * @ClassName: MacHouse
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/10 下午1:16
 * @Version V1.0
 **/
public class MacHouse implements House {

    @Override
    public void output() {
        System.out.println("这是马称的房子");
    }
}
