package com.fc.test.thread.test_create;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @ClassName: Thread_C
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/15 下午2:50
 * @Version V1.0
 **/
public class Thread_C {

    public static void main(String[] args) {
        Callable<Object> oneCallable = new Tickets<Object>();
        FutureTask<Object> oneTask = new FutureTask<>(oneCallable);

        Thread t = new Thread(oneTask);

        t.start();
    }
}

class Tickets<Object> implements Callable<Object> {

    //重写call方法
    @Override
    public Object call() throws Exception {
        // TODO Auto-generated method stub
        System.out.println(Thread.currentThread().getName() + "-->我是通过实现Callable接口通过FutureTask包装器来实现的线程");
        return null;
    }
}
