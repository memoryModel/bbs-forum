package com.fc.test.common;

import com.fc.model.User;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * @ClassName: CommentTest
 * @Description: TODOnet.sf.json.JSONObject
 * @Author maChen
 * @Date 2019/1/21 上午10:40
 * @Version V1.0
 **/
public class CommentTest {

    @Test
    public void test() {
        Jedis jedis = new Jedis("47.94.158.71",7379);
        jedis.auth("buzhidao");
        Pipeline pipeline = jedis.pipelined();

        for (int i = 0; i < 100; i++) {
            Response<Long> response = pipeline.hset("redis-hash","name"+i,"马称");
            System.out.println("response_" + i + "返回内容为：" + response);
        }


        jedis.close();
    }

    @Test
    public void testStartsWith() {
        String Str = new String("www.runoob.com");
        System.out.print("返回值 :" );
        System.out.println(Str.startsWith("www") );
        System.out.print("返回值 :" );
        System.out.println(Str.startsWith("runoob") );
        System.out.print("返回值 :" );
        System.out.println(Str.startsWith("runoob", 4) );
    }


    @Test
    public void save() {
        User user = new User();
        // user = userService.get();

        user.setUid(1L);
        user.setUsername(trimString("222222   "));
        user.setDescription("fhjshf    ");

    }

    public String trimString(String value) {
        // string.trim(value);
        return value;
    }






























}
