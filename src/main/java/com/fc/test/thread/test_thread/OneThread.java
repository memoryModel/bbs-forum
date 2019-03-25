package com.fc.test.thread.test_thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Function: 两个线程交替执行打印 1~100
 *
 * lock 版
 *
 * @author mac
 *         Date: 31/1/2019 10:04
 * @since JDK 1.8
 */
public class OneThread {

    /**
     * 起始值
     */
    private int count = 1;

    /**
     * 标示
     */
    private volatile boolean flag = false;

    /**
     * 重入锁
     */
    private final static Lock LOCK = new ReentrantLock();

    public static void main(String[] args) {
        OneThread oneThread = new OneThread();

        Thread t1 = new Thread(new OddPrint(oneThread));
        t1.setName("t1");

        Thread t2 = new Thread(new EvenPrint(oneThread));
        t2.setName("t2");

        t1.start();
        t2.start();

    }

    public static class OddPrint implements Runnable {

        private OneThread oneThread;

        public OddPrint(OneThread oneThread) {
            this.oneThread = oneThread;
        }

        @Override
        public void run() {
            while (oneThread.count <= 100) {
                if (oneThread.flag) {
                    try {
                        LOCK.lock();
                        System.out.println(Thread.currentThread().getName() + "+-+" + oneThread.count);
                        oneThread.count++;
                        oneThread.flag = false;
                    } finally {
                        LOCK.unlock();
                    }
                }
            }

        }
    }

    public static class EvenPrint implements Runnable {

        private OneThread oneThread;

        public EvenPrint(OneThread oneThread) {
            this.oneThread = oneThread;
        }

        @Override
        public void run() {
            while (oneThread.count <= 100) {
                if (!oneThread.flag) {
                    try {
                        LOCK.lock();
                        System.out.println(Thread.currentThread().getName() + "+-+" + oneThread.count);
                        oneThread.count++;
                        oneThread.flag = true;
                    } finally {
                        LOCK.unlock();
                    }
                }
            }
        }
    }


}
