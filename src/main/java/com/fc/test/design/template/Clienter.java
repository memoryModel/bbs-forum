package com.fc.test.design.template;

/**
 * @ClassName: Clienter
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/9 下午11:02
 * @Version V1.0
 **/
public class Clienter {

    public static void main(String[] args) {
        HouseTemplate houseOne = new HouseOne("别墅", false);

        houseOne.buildHouse();
    }
}
