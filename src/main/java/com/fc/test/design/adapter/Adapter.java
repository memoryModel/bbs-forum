package com.fc.test.design.adapter;

/**
 * @ClassName: Adapter
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/12 下午6:57
 **/
public class Adapter extends Usber implements Ps2 {
    
    @Override
    public void isPs2() {
        isUsb();
    }
}
