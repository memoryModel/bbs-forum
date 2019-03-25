package com.fc.test.thread.programme;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TicketSeller1
 * @Description: 十个窗口对外卖10000张票 多线程下可能会出现重复卖，以及超量卖情况
 * @Author maChen
 * @Date 2019/2/25 上午10:47
 * @Version V1.0
 **/
public class TicketSeller1 {

    static List<String> tickets = new ArrayList<>(10000);

    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("票编号：" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (tickets.size() > 0) {
                    System.out.println("销售了--" + tickets.remove(0));
                }
            }).start();
        }
    }

}
