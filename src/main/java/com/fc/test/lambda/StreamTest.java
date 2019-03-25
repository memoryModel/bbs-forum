package com.fc.test.lambda;

import java.util.stream.Stream;

/**
 * @ClassName: StreamTest
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/11 下午3:45
 **/
public class StreamTest {

    public static void main(String[] args) {
        Stream<String> s = Stream.of("test", "t1", "t2", "teeeee", "aaaa");
        // 查找所有包含t的元素并进行打印
        s.filter(n -> n.contains("t")).forEach(System.out::println);
    }
}
