package com.fc.test.thread.produceconsume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ProduceConsume2
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/28 上午10:10
 * @Version V1.0
 **/
public class ProduceConsume2 {

    private static final Logger log = LoggerFactory.getLogger(ProduceConsume2.class);

    private static List<String> locks = new ArrayList<>();

    public static void main(String[] args) {
        
        Consume consume1 = new Consume(locks);
        consume1.setName("消费线程1");
        Consume consume2 = new Consume(locks);
        consume2.setName("消费线程2");
        Produce produce = new Produce(locks);
        produce.setName("生产线程");

        consume1.start();
        consume2.start();
        produce.start();




    }

    static class Consume extends Thread {

        private List<String> locks;

        public Consume(List<String> locks) {
            this.locks = locks;
        }

        @Override
        public void run() {
            synchronized (locks) {
                while (locks.isEmpty()) {
                    log.info(Thread.currentThread().getName() + " 调用wait方法");
                    try {
                        locks.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info(Thread.currentThread().getName() + "  wait方法结束");
                }

                String element = locks.remove(0);
                log.info(Thread.currentThread().getName() + " 取出第一个元素为：" + element);
            }
        }
    }

    static class Produce extends Thread {

        private List<String> locks;

        public Produce(List<String> locks) {
            this.locks = locks;
        }

        @Override
        public void run() {
            synchronized (locks) {
                log.info(Thread.currentThread().getName() + " 开始添加元素");
                locks.add(Thread.currentThread().getName());
                locks.notifyAll();
            }
        }
    }
}
