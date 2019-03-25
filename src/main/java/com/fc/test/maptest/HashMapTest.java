package com.fc.test.maptest;

import com.fc.test.design.template.Student;
import org.junit.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @ClassName: HashMapTest
 * @Description: TODO
 * @Author mac
 * @Date 2019/3/20 上午9:28
 **/
public class HashMapTest {

    private Map<String, Object> map = new HashMap<>();

    private Map<String, Object> table = new Hashtable<>();

    /**
     * 验证hashMap可以为空
     */
    @Test
    public void test1() {
        map.put(null,"哈哈");
        map.put(null,"哈哈1");
        String str = map.get(null).toString();
        System.out.println(str);

        table.put(null,"哈哈");

    }
}
