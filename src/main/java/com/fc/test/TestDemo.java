package com.fc.test;

import com.fc.test.design.template.Student;
import org.junit.Test;

import java.util.*;

/**
 * @ClassName: Test
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/20 下午4:35
 **/
public class TestDemo {

    private int name;

    @Test
    public void test() {
        Student student = new Student();

        Map<Student, String> stringMap = new HashMap<>(16);
        stringMap.put(student,"s");

        System.out.println("对象未改变查找结果：" + stringMap.get(student));

        student.setId(1L);

        System.out.println("对象改变后查找结果：" + stringMap.get(student));

        student.setId(null);

        System.out.println(stringMap.get(student));
    }

    @Test
    public void test2() {

    }

    @Test
    public void test3() {
        String[] strings = {"private","public","string","int","byte","abc","head","tail","live","bleak","student","class"};
        List<Integer> integers = new ArrayList<>();

        Arrays.asList(strings).forEach(str -> {
            int h;
            int hash = (h = str.hashCode()) ^ (h >>> 16);
            System.out.println("未经过hash函数之前的hashCode：" + str.hashCode());
            System.out.println("经过hash函数摧残之后的hashCode：" + hash);
            int length = 15 & hash;

            if (integers.contains(length)) {
                System.out.println("键值：" + str + "插入重复");
            }
            integers.add(length);
            System.out.println("插入元素：" + str + "位置：" + length);
        });
    }

    @Test
    public void test4() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 2147483647; i++) {
            int result = 1 * 2;
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        long startTime1 = System.currentTimeMillis();
        for (int i = 0; i < 2147483647; i++) {
            int result = 1 << 1;
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println(endTime1 - startTime1);

    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();

        map.put(null, "hello1");

        map.put("head","hello");

        System.out.println(15 & ("head".hashCode() ^ ("head".hashCode() >>> 16)));

        System.out.println(map.get(null));
        System.out.println(map.get("head"));
    }
}
