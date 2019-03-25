package com.fc.test.thread.test_thread;

import org.junit.Test;

/**
 * @ClassName: FourThread
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/1 下午5:37
 * @Version V1.0
 **/
public class FourThread {

    static int num = 0;
    static boolean flag = false;

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            for (; 100 > num;) {
                if (!flag && (num == 0 || ++num % 2 == 0)) {
                    System.out.println(num);
                    flag = true;
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (; 100 > num; ) {
                if (flag && (++num % 2 != 0)) {
                    System.out.println(num);
                    flag = false;
                }
            }
        });


        t1.start();
        t2.start();
    }

    @Test
    public void test() {
        int num = 0;
        for (; 100 > num;){
            System.out.println(num);
            num++;
        }
    }
}
