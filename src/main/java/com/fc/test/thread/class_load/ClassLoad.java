package com.fc.test.thread.class_load;

/**
 * @ClassName: ClassLoad
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/22 下午4:27
 * @Version V1.0
 **/
public class ClassLoad {

    public static void main(String[] args) {
        String s = new String();
        System.out.println(ClassLoad.class.getClassLoader());
        Runtime.getRuntime().gc();
    }
}
