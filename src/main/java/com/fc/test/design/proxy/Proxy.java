package com.fc.test.design.proxy;

import com.fc.test.design.decorator.House;
import com.fc.test.design.decorator.MacHouse;

/**
 * @ClassName: Proxy
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/10 下午1:54
 * @Version V1.0
 **/
public class Proxy implements House {

    private House house;

    public Proxy(MacHouse house) {
        this.house = house;
    }

    @Override
    public void output() {
        System.out.println("代理模式：这是针对房子的前段代理增强");
        house.output();
        System.out.println("代理模式：这是针对房子的后段代理增强");
    }
}
