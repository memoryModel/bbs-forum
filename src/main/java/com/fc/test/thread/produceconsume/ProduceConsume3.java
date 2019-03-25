package com.fc.test.thread.produceconsume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: ProduceConsume3
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/28 上午10:58
 * @Version V1.0
 **/
public class ProduceConsume3 {

    private static final Logger log = LoggerFactory.getLogger(ProduceConsume2.class);

    private static List<Integer> list = new ArrayList<>();
    private static Integer maxLength = 8;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(15);
        for (int i = 0; i < 5; i++) {
            executor.execute(new Produce(list, maxLength));
        }

        for (int i = 0; i < 10; i++) {
            executor.execute(new Consume(list));
        }
        executor.shutdown();
    }

    static class Produce implements Runnable {

        private List<Integer> list;
        private Integer max;

        public Produce (List<Integer> list, Integer max) {
            this.list = list;
            this.max = max;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (list) {
                    try {
                        while (list.size() == max) {
                            log.info("生产者" + Thread.currentThread().getName() + "  list以达到最大容量，进行wait");
                            list.wait();
                            log.info("生产者" + Thread.currentThread().getName() + "  退出wait");
                        }

                        Random random = new Random();
                        Integer i = random.nextInt();
                        list.add(i);
                        log.info("生产者" + Thread.currentThread().getName() + " 生产数据" + i);
                    }
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    static class Consume implements Runnable {

        private List<Integer> list;

        public Consume (List<Integer> list) {
            this.list = list;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (list) {
                    try {
                        while (list.isEmpty()) {
                            log.info("消费者" + Thread.currentThread().getName() + "  list为空，进行wait");
                            list.wait();
                            log.info("消费者" + Thread.currentThread().getName() + "  退出wait");
                        }

                        Integer element = list.remove(0);
                        log.info("消费者" + Thread.currentThread().getName() + "  消费数据：" + element);
                        list.notifyAll();
                    }
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
