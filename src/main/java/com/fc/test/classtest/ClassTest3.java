package com.fc.test.classtest;

/**
 * @ClassName: ClassTest3
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/18 上午9:54
 * @Version V1.0
 **/
public class ClassTest3 {

    private static class InstanceHolder {
        public static ClassTest3 classTest3 = new ClassTest3();
    }

    public static ClassTest3 getClassTest() {
        return InstanceHolder.classTest3;
    }

    public static void main(String[] args) {
        ClassTest3 classTest3 = ClassTest3.getClassTest();
        System.out.println(classTest3);
    }
}
