package com.fc.test.sync;

/**
 * @ClassName: OneSynchronized
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/2 下午1:27
 * @Version V1.0
 **/
public class OneSynchronized {

    static {
        synchronized (OneSynchronized.class) {

        }
    }

    public static void save() {

    }
}
