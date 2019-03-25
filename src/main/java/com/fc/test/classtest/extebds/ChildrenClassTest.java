package com.fc.test.classtest.extebds;

import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName: ChildrenClassTest
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/9 下午10:54
 * @Version V1.0
 **/
public class ChildrenClassTest extends ParentClassTest {

    public boolean ifDelete(String str) {
        System.out.println("haha");
        return false;
    }

    @Override
    public void buildClass() {

    }

    @Override
    public void ExceptionTest () {

    }

    @Test
    public void test() {
        String a = "hello2";
        String b = "hello";
        String c = "hello" + 2;
        System.out.println(a == c);
        byte[] aaa,bbbb,cccc;

    }

}
