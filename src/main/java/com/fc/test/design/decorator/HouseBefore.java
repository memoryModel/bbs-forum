package com.fc.test.design.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: HouseBefore
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/10 下午2:17
 * @Version V1.0
 **/
@Slf4j
public class HouseBefore extends Decorator {

    public HouseBefore(House house) {
        super(house);
    }

    @Override
    public void output() {
        before();
        super.output();
    }

    public void before() {
        log.info("装饰者模式：这是针对房子的前段装饰增强");
    }

}
