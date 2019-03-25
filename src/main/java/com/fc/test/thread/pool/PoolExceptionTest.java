package com.fc.test.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: PoolExceptionTest
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/12 上午9:53
 **/
public class PoolExceptionTest {

    static final ExecutorService pool = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        pool.submit(new ThreadTest());
        pool.shutdown();
    }
}

class ThreadTest implements Runnable {

    @Override
    public void run() {

        int i = 1/0;

    }
}
