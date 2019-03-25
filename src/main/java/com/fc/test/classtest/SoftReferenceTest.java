package com.fc.test.classtest;

import org.junit.Test;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @ClassName: SoftReferenceTest
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/14 下午5:40
 **/
public class SoftReferenceTest {

    public static void main(String[] args) {
        // 软引用
        SoftReference<String> sr = new SoftReference<>(new String("hello"));

        System.out.println("GC回收之前：" + sr.get());
        // 通知JVM的gc进行垃圾回收
        System.gc();
        System.out.println("GC回收之后:" + sr.get());

        // 弱引用
        WeakReference<String> wr = new WeakReference<String>(new String("hello"));

        System.out.println(wr.get());
        // 通知JVM的gc进行垃圾回收
        System.gc();
        System.out.println(wr.get());

        String str = "hello";

        System.out.println(str);
        // 通知JVM的gc进行垃圾回收
        System.gc();
        System.out.println(str);
    }

    @Test
    public void test() {
        int i = 16 << 2;
        System.out.println(i);
    }
}
