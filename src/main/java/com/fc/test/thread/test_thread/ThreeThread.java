package com.fc.test.thread.test_thread;

/**
 * Function: 两个线程交替执行打印 1~100
 *
 * 不加锁版
 *
 * @author mac
 * @Date: 31/1/2019 10:04
 * @since JDK 1.8
 */
public class ThreeThread {

    /**
     * 起始值
     */
    private int count = 1;

    /**
     * 标示
     */
    private boolean flag = true;

    public static void main(String[] args) {

        ThreeThread twoThread = new ThreeThread();

        Thread t1 = new Thread(new OddPrint(twoThread));
        t1.setName("t1");

        Thread t2 = new Thread(new EvenPrint(twoThread));
        t2.setName("t2");

        t1.start();
        t2.start();

    }

    public static class OddPrint implements Runnable {

        private ThreeThread twoThread;

        public OddPrint(ThreeThread twoThread) {
            this.twoThread = twoThread;
        }

        @Override
        public void run() {
            while (twoThread.count <= 99) {
                if (twoThread.flag) {
                    System.out.println(Thread.currentThread().getName() + "+-+" + twoThread.count);
                    twoThread.count++;
                    twoThread.flag = false;
                }
            }
        }
    }

    public static class EvenPrint implements Runnable {

        private ThreeThread twoThread;

        public EvenPrint(ThreeThread twoThread) {
            this.twoThread = twoThread;
        }

        @Override
        public void run() {
            while (twoThread.count <= 100) {
                if (!twoThread.flag) {
                    System.out.println(Thread.currentThread().getName() + "+-+" + twoThread.count);
                    twoThread.count++;
                    twoThread.flag = true;
                }
            }
        }
    }
}
