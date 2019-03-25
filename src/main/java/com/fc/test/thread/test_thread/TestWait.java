package com.fc.test.thread.test_thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * @ClassName: TestWait
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/21 下午1:38
 * @Version V1.0
 **/
public class TestWait {

    private static Log log = LogFactory.getLog(TestWait.class);

    public synchronized void create() throws InterruptedException {
        System.out.println("进入了wait");
        wait();

        log.info("被唤醒");
    }

    public synchronized void update() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyAll();

        log.info("释放锁");
    }

    @Test
    public void test() throws InterruptedException {
        TestWait test = new TestWait();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    test.create();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test.update();
            }
        }).start();

        Thread.sleep(5000);
    }
}
