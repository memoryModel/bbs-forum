package com.fc.test.thread.test_create;

/**
 * @ClassName: thread_a
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/15 下午2:42
 * @Version V1.0
 **/
public class Thread_A extends Thread {

    @Override
    public void run() {
        System.out.println("我是一个自定义线程");
        //int i = 1 / 0;
        System.out.println("我还没有运行就结束了");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new Thread_A());
        thread.start();
    }
}
