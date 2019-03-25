package com.fc.test.design.decorator;

import com.fc.test.design.proxy.Proxy;
import org.junit.Test;

/**
 * @ClassName: Clienter
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/10 下午1:34
 * @Version V1.0
 **/
public class Clienter {

    public static void main(String[] args) {
        House house = new MacHouse();
        Decorator decorator = new Decorator(house);

        decorator.output();

        MacHouse macHouse = new MacHouse();
        Proxy proxy = new Proxy(macHouse);
        proxy.output();

    }

    @Test
    public void decoratorTest() {
        House house = new HouseAfter(new HouseBefore(new MacHouse()));
        house.output();
    }
}
