package com.fc.test.classtest;

/**
 * @ClassName: ClassTest2
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/16 下午6:00
 * @Version V1.0
 **/
public class ClassTest2 {

    private String name = "马称";

    public ClassTest2() {
        System.out.println("ClassTest2类进行了加载");
    }

    public ClassDemo createDemo() {
        return new ClassDemo();
    }

    class ClassDemo {
        private String name = "马称Demo";
    }

    public static void main(String[] args) {
        ClassTest2 classTest2 = new ClassTest2();
        ClassTest2.ClassDemo classDemo = classTest2.new ClassDemo();

    }
}
