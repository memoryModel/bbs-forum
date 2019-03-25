package com.fc.test.thread.programme;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @ClassName: TicketSeller2
 * @Description: 十个窗口对外卖10000张票
 * @Author maChen
 * @Date 2019/2/25 上午10:47
 * @Version V1.0
 **/
public class TicketSeller2 {

    static Queue<String> tickets = new ConcurrentLinkedQueue<>();

    static int max = 100;

    static {
        for (int i = 0; i < max; i++) {
            tickets.add("票编号：" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    String result = tickets.poll();
                    if (result == null) {
                        break;
                    } else {
                        System.out.println("销售了--" + result);
                    }
                }
            }).start();
        }
    }

}
