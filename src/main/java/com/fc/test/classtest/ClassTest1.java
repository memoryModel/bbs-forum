package com.fc.test.classtest;

/**
 * @ClassName: ClassTest1
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/16 下午5:47
 * @Version V1.0
 **/
public class ClassTest1 {
    public static class ClassTest2 {
        static {
            System.out.println("静态内部类被初始化了");
        }
        public static ClassTest1 classTest1 = new ClassTest1();
    }

    public static ClassTest1 create() {
        return ClassTest2.classTest1;
    }

    public static void main(String[] args) {
        ClassTest1 classTest1 = new ClassTest1();
        classTest1.create();

    }
}
