package com.fc.test.thread.assist;

import sun.misc.InnocuousThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ClassName: SemaphoreDemo
 * @Description: Semaphore实例
 * @Author mac
 * @Date 2019/3/21 上午9:25
 **/
public class SemaphoreDemo {
    private static final Semaphore SEMAPHORE = new Semaphore(3);
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);

    private static class InformationThread implements Runnable {

        private final String name;
        private final int age;

        public InformationThread (String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public void run() {
            try {
                SEMAPHORE.acquire();
                System.out.println(Thread.currentThread().getName()+":大家好，我是"+name+"我今年"+age+"岁当前时间为："+System.currentTimeMillis());
                Thread.sleep(1000);
                System.out.println(name+"要准备释放许可证了，当前时间为："+System.currentTimeMillis());
                System.out.println("当前可使用的许可数为："+SEMAPHORE.availablePermits());
                SEMAPHORE.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String[] name= {"李明","王五","张杰","王强","赵二","李四","张三"};
        int[] age= {26,27,33,45,19,23,41};
        int MAX = 7;

        for (int i = 0; i < MAX; i++) {
            Runnable runnable = new InformationThread(name[i], age[i]);
            EXECUTOR_SERVICE.execute(runnable);
        }
    }
}
