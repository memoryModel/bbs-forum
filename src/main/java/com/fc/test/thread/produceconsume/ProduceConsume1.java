package com.fc.test.thread.produceconsume;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @ClassName: ProduceConsume1
 * @Description: 生产消费 基于LinkedBlockingQueue
 * @Author maChen
 * @Date 2019/2/25 下午5:02
 * @Version V1.0
 **/
public class ProduceConsume1 {

    static BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
    static Random random = new Random();

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                try {
                    blockingQueue.put("数值：" + i);
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "take -- " + blockingQueue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
