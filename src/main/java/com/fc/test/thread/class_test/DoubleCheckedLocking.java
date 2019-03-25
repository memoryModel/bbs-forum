package com.fc.test.thread.class_test;

/**
 * @ClassName: DoubleCheckedLocking
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/18 上午9:00
 * @Version V1.0
 **/
public class DoubleCheckedLocking {

    private static DoubleCheckedLocking instance;
    public static DoubleCheckedLocking getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedLocking.class) {
                if (instance == null) {
                    instance = new DoubleCheckedLocking();
                }
            }
            return instance;
        }
        return null;
    }
}
