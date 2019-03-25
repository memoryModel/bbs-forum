package com.fc.test.thread.test_thread;

/**
 * @ClassName: FiveThread
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/13 上午8:19
 * @Version V1.0
 **/
public class FiveThread {

    private int start = 1;
    private boolean flag = true;

    public static void main(String[] args) {
        FiveThread fiveThread = new FiveThread();

        Thread t1 = new Thread(new JiNum(fiveThread));
        Thread t2 = new Thread(new OuNum(fiveThread));

        t1.setName("A");
        t2.setName("B");

        t1.start();
        t2.start();
    }

    /**
     * 奇数线程
     */
    public static class JiNum implements Runnable {

        private FiveThread fiveThread;

        public JiNum(FiveThread fiveThread) {
            this.fiveThread = fiveThread;
        }

        @Override
        public void run() {

            while (fiveThread.start <= 100) {
                synchronized (FiveThread.class) {
                    System.out.println("奇数线程抢到锁了");
                    if (!fiveThread.flag) {
                        System.out.println(Thread.currentThread().getName() + "+-+奇数" + fiveThread.start);
                        fiveThread.start++;
                        fiveThread.flag = true;
                        FiveThread.class.notify();
                    }else {
                        try {
                            FiveThread.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }

    /**
     * 偶数线程
     */
    public static class OuNum implements Runnable {

        private FiveThread fiveThread;

        public OuNum(FiveThread fiveThread) {
            this.fiveThread = fiveThread;
        }

        @Override
        public void run() {

            while (fiveThread.start <= 99) {
                try {
                    Thread.sleep(50);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                synchronized (FiveThread.class) {
                    System.out.println("偶数线程抢到锁了");
                    if (fiveThread.flag) {
                        System.out.println(Thread.currentThread().getName() + "+-+偶数" + fiveThread.start);
                        fiveThread.start++;

                        fiveThread.flag = false;
                        FiveThread.class.notify();
                    } else {
                        try {
                            FiveThread.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
