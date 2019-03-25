package com.fc.test.thread.test_thread;

/**
 * @ClassName: WaitNotifyTest
 * @Description: 测试wait()、notify()方法
 * @Author maChen
 * @Date 2019/2/21 下午4:41
 * @Version V1.0
 **/
public class WaitNotifyErrorTest {

    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();

        synchronized (lock1) {
            try {
                lock2.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
