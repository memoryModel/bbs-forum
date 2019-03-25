package com.fc.test.thread.pool;

import java.util.concurrent.Executor;

/**
 * @ClassName: MyExecutor
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/27 下午5:01
 * @Version V1.0
 **/
public class MyExecutor implements Executor {


    @Override
    public void execute(Runnable command) {
        command.run();
    }

    public static void main(String[] args) {
        new MyExecutor().execute(() -> System.out.println("Executor执行"));
    }
}
