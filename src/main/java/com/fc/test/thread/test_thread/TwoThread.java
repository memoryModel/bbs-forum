package com.fc.test.thread.test_thread;

/**
 * Function: 两个线程交替执行打印 1~100
 *
 * synchronized 版
 *
 * @author mac
 *         Date: 31/1/2019 10:04
 * @since JDK 1.8
 */
public class TwoThread {

    /**
     * 起始值
     */
    private Integer count = 1;

    /**
     * 标示
     */
    private volatile boolean flag = false;

    /**
     * 锁定对象
     */
    private static byte[] bytes = new byte[0];

    public static void main(String[] args) {

        TwoThread twoThread = new TwoThread();

        Thread t1 = new Thread(new OddPrint(twoThread));
        t1.setName("t1");

        Thread t2 = new Thread(new EvenPrint(twoThread));
        t2.setName("t2");

        t1.start();
        t2.start();

    }

    public static class OddPrint implements Runnable {

        private TwoThread twoThread;

        public OddPrint(TwoThread twoThread) {
            this.twoThread = twoThread;
        }

        @Override
        public void run() {
            while (twoThread.count <= 100) {
                if (twoThread.flag) {
                    synchronized (twoThread.count) {
                        System.out.println(Thread.currentThread().getName() + "+-+" + twoThread.count);
                        twoThread.count++;
                        twoThread.flag = false;
                    }
                }
            }
        }
    }

    public static class EvenPrint implements Runnable {

        private TwoThread twoThread;

        public EvenPrint(TwoThread twoThread) {
            this.twoThread = twoThread;
        }

        @Override
        public void run() {
            while (twoThread.count <= 100) {
                if (!twoThread.flag) {
                    synchronized (twoThread.count) {
                        System.out.println(Thread.currentThread().getName() + "+-+" + twoThread.count);
                        twoThread.count++;
                        twoThread.flag = true;
                    }
                }
            }
        }
    }


}
