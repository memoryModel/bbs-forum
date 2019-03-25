package com.fc.test.thread.pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: MyPool
 * @Description: TODO
 * @Author maChen
 * @Date 2019/3/2 下午1:24
 * @Version V1.0
 **/
public class MyPool {

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 20, 20L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1024));

        System.out.println(2|1);
    }
}
