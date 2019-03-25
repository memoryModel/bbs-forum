package com.fc.test.thread.test_thread;

/**
 * @ClassName: WaitNotifyTest
 * @Description: 测试wait()、notify()方法
 * wait()方法被唤醒之后，从wait()方法下继续执行
 * @Author maChen
 * @Date 2019/2/21 下午5:20
 * @Version V1.0
 **/
public class WaitNotifyTest1 {

    private final Object lock = new Object();

    public void add() {
        synchronized (lock) {
            try {
                System.out.println("即将执行wait()");
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("从wait中恢复");

        }
    }

    public void update() {
        synchronized (lock) {
            lock.notify();
            System.out.println("执行了notify()");
        }
    }

    public static void main(String[] args) {
        WaitNotifyTest1 classTest = new WaitNotifyTest1();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                classTest.add();
            }
        });

        thread.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                classTest.update();
            }
        });

        thread2.start();
    }
}
