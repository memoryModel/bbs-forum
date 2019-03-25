package com.fc.test.design.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: HouseAfter
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/10 下午2:20
 **/
@Slf4j
public class HouseAfter extends Decorator {

    public HouseAfter(House house) {
        super(house);
    }

    @Override
    public void output() {
        super.output();
        after();
    }

    public void after() {
        log.info("装饰者模式：这是针对房子的后段装饰增强");
    }
}
