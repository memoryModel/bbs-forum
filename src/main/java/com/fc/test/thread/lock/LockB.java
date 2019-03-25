package com.fc.test.thread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: LockB
 * @Description: 使用Lock与Condition编写程序
 * @Author maChen
 * @Date 2019/2/27 上午10:49
 * @Version V1.0
 **/
public class LockB {

    static final Lock LOCK = new ReentrantLock();
    static final Condition CONDITION = LOCK.newCondition();

    public static void interruptedTest() throws InterruptedException {
        Thread.sleep(1000000);

        System.out.println("被中断后继续执行了");
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {

        }).start();

        Thread thread = new Thread(() -> {
            try {
                LockB.interruptedTest();
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
        thread.join();

        CONDITION.await();

        System.out.println("抛出异常后继续执行main方法");

    }

}
