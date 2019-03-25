package com.fc.test.thread.test_create;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: Thread_D
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/15 下午2:58
 * @Version V1.0
 **/
public class Thread_D {

    /**
     * 线程池数量
     */
    private static int POOL_NUM = 10;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < POOL_NUM; i++) {
            RunnableThread thread = new RunnableThread();

            executorService.execute(thread);
        }

        executorService.shutdown();
    }
}

class RunnableThread implements Runnable {

    @Override
    public void run() {
        System.out.println("通过线程池方式创建的线程：" + Thread.currentThread().getName() + " ");
    }
}
