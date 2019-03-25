package com.fc.test.thread.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: LockA
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/19 下午5:21
 * @Version V1.0
 **/
public class LockA {

    private static CountDownLatch latch = new CountDownLatch(5);

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "进行了countDown()");
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
        System.out.println("结束了哦");
    }
}
