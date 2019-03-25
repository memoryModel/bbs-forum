package com.fc.test.design.decorator;

/**
 * @ClassName: Decorator
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/10 下午1:09
 * @Version V1.0
 **/
public class Decorator implements House {

    private House house;

    public Decorator(House house) {
        this.house = house;
    }

    @Override
    public void output() {
        house.output();
    }
}
