package com.fc.test.thread.final_test;

/**
 * @ClassName: FinalExample
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/11 上午10:12
 * @Version V1.0
 **/
public class FinalExample {

    int i;
    final int j;

    static FinalExample obj;

    public FinalExample() {
        i = 1;
        j = 2;
    }

    public static void writer() {
        obj = new FinalExample();
    }

    public static void reader() {
        FinalExample object = obj;
        int a = object.i;
        int b= object.j;
        System.out.println();
    }

    public static void main(String[] args) {
        

    }
}
