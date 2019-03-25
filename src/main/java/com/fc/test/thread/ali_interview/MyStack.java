package com.fc.test.thread.ali_interview;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MyStack
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/21 下午1:28
 * @Version V1.0
 **/
public class MyStack {

    private List<String> list = new ArrayList<>();

    private synchronized void push(String value) {
        synchronized (this) {
            list.add(value);
            notify();
        }
    }

    public synchronized String pop() throws InterruptedException {
        synchronized (this) {
            if (list.size() <= 0) {
                wait();
            }
        }

        return list.remove(list.size() -1);
    }

    public static void main(String[] args) {

    }
}
