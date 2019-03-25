package com.fc.test.thread.test_create;

/**
 * @ClassName: Thread_B
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/15 下午2:46
 * @Version V1.0
 **/
public class Thread_B implements Runnable {
    @Override
    public void run() {
        System.out.println("我还是一个自定义的线程");
    }

    public static void main(String[] args) {
        Thread_B b = new Thread_B();
        Thread thread = new Thread(b);
        thread.start();
    }
}
