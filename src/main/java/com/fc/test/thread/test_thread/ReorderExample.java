package com.fc.test.thread.test_thread;

import org.junit.Test;

/**
 * @ClassName: ReorderExample
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/6 下午10:57
 * @Version V1.0
 **/
public class ReorderExample {

    static int a = 0;

    static boolean flag = false;

    static int i = 0;

    public static void writer() {
        a = 1;
        flag = true;
    }

    public static void reader() {

        if (flag) {
            i = a*a;
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            writer();
        });

        Thread t2 = new Thread(() -> {
            /*try {
                Thread.sleep(1000);
                System.out.println(a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            reader();
        });

        t1.start();
        t2.start();


        System.out.println(i);
    }
}
