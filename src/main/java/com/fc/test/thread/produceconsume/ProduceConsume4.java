package com.fc.test.thread.produceconsume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @ClassName: ProduceConsume4
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/28 上午11:37
 * @Version V1.0
 **/
public class ProduceConsume4 {

    private static final Logger log = LoggerFactory.getLogger(ProduceConsume4.class);

    private static BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(10);
    private static ExecutorService service = Executors.newFixedThreadPool(15);

    public static void main(String[] args) {
        int produceMax = 5;
        int consumeMax = 10;
        for (int i = 0; i < produceMax; i++) {
            service.execute(new Produce());
        }

        for (int i = 0; i < consumeMax; i++) {
            service.execute(new Consume());
        }
    }

    static class Produce implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Random random = new Random();
                    Integer integer = random.nextInt(1000);
                    blockingQueue.put(integer);
                    log.info(Thread.currentThread().getName() + "插入元素:" + integer);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    static class Consume implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Integer element = blockingQueue.take();
                    log.info(Thread.currentThread().getName() + "取出元素：" + element);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
