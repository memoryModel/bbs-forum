package com.fc.commons.redis;

import com.fc.model.User;
import com.fc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: RedisTest
 * @Description: TODO
 * @Author maChen
 * @Date 2019/1/20 上午8:30
 * @Version V1.0
 **/
public class RedisTest {

    public static void main(String[] args) throws InterruptedException {

        RedisCluster redis = new RedisCluster();
        /*for (int i = 0; i < 2000; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //redisCluster.lphsh("redis-list","user_");
                    redisCluster.hincrBy("redis-hash","count",1);
                }
            });

            thread.start();
        }*/

        String key = RedisKey.POST_INFO+31;
        Map<String, String> map = redis.hgetall(key);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            redis.hdel(key,entry.getKey());
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }


    }
}
