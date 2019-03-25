package com.fc.test.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: ThreadPool_A
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/27 下午5:24
 * @Version V1.0
 **/
public class ThreadPool_A {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 6; i++) {
            service.execute(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("当前线程：" + Thread.currentThread().getName());
            });

            System.out.println("shutdown前：" + service);
            service.shutdown();
            System.out.println("shutdown后：" + service);
        }
    }
}
