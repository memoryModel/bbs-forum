package com.fc.test.sync;
import java.util.concurrent.*;

/**
 * @ClassName: TwoSync
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/14 上午8:48
 * @Version V1.0
 **/
public class TwoSync implements Runnable {

    private static byte[] bytes = new byte[0];
    private int count = 0;

    @Override
    public void run() {
        synchronized (bytes) {
            for (int i = 0; i < 5; i++) {
                count++;
                System.out.println(Thread.currentThread().getName() + ":" + count);
            }
        }
    }

    public static void main(String[] args) {

        Thread t1 = new Thread(new TwoSync(),"线程1");
        Thread t2 = new Thread(new TwoSync(),"线程2");

        t1.start();
        t2.start();

    }
}
