package com.fc.test.thread.local;

/**
 * @ClassName: Test
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/18 上午11:31
 * @Version V1.0
 **/
public class Test {

    private static final ThreadLocal<Long> longLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> stringLocal = new ThreadLocal<>();

    public void set() {
        longLocal.set(Thread.currentThread().getId());
        stringLocal.set(Thread.currentThread().getName());
    }

    public Long getLong() {
            return longLocal.get();
    }

    public String getString() {
        return stringLocal.get();
    }

    public static void main(String[] args) throws InterruptedException {
        final Test test = new Test();

        test.set();
        System.out.println(test.getLong());
        System.out.println(test.getString());

        Thread thread = new Thread(new ThreadDemo(test));
        thread.setName("马百万 嗯哼");
        thread.start();
        thread.join();

        System.out.println(test.getLong());
        System.out.println(test.getString());


    }
}

class ThreadDemo implements Runnable {

    private Test test;

    public ThreadDemo(Test test) {
        this.test = test;
    }

    @Override
    public void run() {
        test.set();
        System.out.println(test.getLong());
        System.out.println(test.getString());
    }
}
