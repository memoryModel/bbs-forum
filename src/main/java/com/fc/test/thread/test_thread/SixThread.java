package com.fc.test.thread.test_thread;

import java.util.LinkedList;

/**
 * @ClassName: SixThread
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/15 上午8:42
 * @Version V1.0
 **/
public class SixThread {

    public int MAX_VALUE = 10;
    private LinkedList<Object> list = new LinkedList<>();

    /**
     * 生产者
     */
    public void product() {
        synchronized (list) {
            while (MAX_VALUE > list.size()) {
                System.out.println("生产者线程：" + Thread.currentThread().getName());
                list.add(new Object());
                System.out.println(Thread.currentThread().getName()+"新增成功，当前容器剩余数量为：" + list.size());
                try {
                    list.notifyAll();
                    Thread.sleep(80);
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



            }
        }


    }

    /**
     * 消费者
     */
    public void costomer() {

        synchronized (list) {
            while (list.size() > 0) {
                System.out.println("消费者线程：" + Thread.currentThread().getName());
                try {
                    list.pop();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"消费成功，当前容器剩余数量为：" + list.size());
                try {
                    list.notifyAll();
                    Thread.sleep(50);
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public static void main(String[] args) {
        SixThread sixThread = new SixThread();


        for (int i = 0; i < 8; i++) {
            new Thread(() -> {
                sixThread.product();
            }).start();

        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                sixThread.costomer();
            }).start();

        }

    }


}
