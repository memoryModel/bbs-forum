package com.fc.test.thread.class_test;

/**
 * @ClassName: ChildClass
 * @Description: TODO
 * @Author maChen
 * @Date 2019/2/20 上午8:44
 * @Version V1.0
 **/
public class ChildClass extends ParentClass {

    @Override
    public void add() {
        System.out.println("哈哈1");
    }

    /*public void create() {

    }*/

    public void delete() {
        System.out.println("哈哈");
    }

    public static void main(String[] args) {
        ChildClass c = new ChildClass();
        c.add();
    }
}
