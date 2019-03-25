package com.fc.test.thread.class_test;

import com.fc.model.User;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: Test
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/20 上午9:26
 * @Version V1.0
 **/
public class Test {

    static AtomicInteger integer = new AtomicInteger();
    public static void main(String[] args) {
        /*String str = "192.168.3.12";
        String last = String.valueOf(Integer.parseInt(str.substring(str.lastIndexOf(".")+1)) + 1);
        String first = str.substring(0,str.lastIndexOf(".")+1);
        System.out.println(str = first + last);*/

        for (int i = 0; i < 10; i++) {
            ThreadA thread = new ThreadA();
            Thread thread1 = new Thread(thread);
            thread1.start();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(integer);
    }

    static class ThreadA implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                integer.incrementAndGet();
            }
        }
    }


}


