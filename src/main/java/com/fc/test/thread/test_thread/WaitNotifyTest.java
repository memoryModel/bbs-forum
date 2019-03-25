package com.fc.test.thread.test_thread;

/**
 * @ClassName: WaitNotifyTest
 * @Description: 测试wait()、notify()方法
 * @Author maChen
 * @Date 2019/2/21 下午4:41
 * @Version V1.0
 **/
public class WaitNotifyTest implements Runnable {

    private int num;
    private Object lock;

    public WaitNotifyTest(int num, Object lock) {
        this.num = num;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (lock) {
                    lock.notify();
                    lock.wait();
                    System.out.println("当前线程："+Thread.currentThread().getName() + "打印num：" + num);
                }
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final Object lock = new Object();

        Thread t1=  new Thread(new WaitNotifyTest(1,lock));
        Thread t2=  new Thread(new WaitNotifyTest(2,lock));
        Thread t3=  new Thread(new WaitNotifyTest(3,lock));

        t1.start();
        t2.start();
        t3.start();
    }
}
