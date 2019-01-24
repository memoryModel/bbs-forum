package com.fc;

import com.fc.commons.redis.RedisCluster;
import com.fc.commons.redis.RedisKey;
import com.fc.commons.redis.SerializeUtil;
import com.fc.commons.status.PostListType;
import com.fc.model.Comment;
import com.fc.model.Reply;
import com.fc.model.User;
import com.google.gson.JsonObject;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import redis.clients.jedis.Jedis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @ClassName: CommentTest
 * @Description: TODOnet.sf.json.JSONObject
 * @Author maChen
 * @Date 2019/1/21 上午10:40
 * @Version V1.0
 **/
public class CommentTest {

    public static void main(String[] args) throws InterruptedException, ParseException {
        /*RedisCluster redis = new RedisCluster();
        redis.hset(RedisKey.USER_INFO+2,"uid","2");
        redis.hset(RedisKey.USER_INFO+2,"userName","桃谷绘理香");
        redis.hset(RedisKey.USER_INFO+2,"headUrl","https://t1.hddhhn.com/uploads/tu/201901/306/1.jpg");*/

        /*String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse("2019-12-22 11:09:59");
        long ts = date.getTime();
        System.out.println(res = String.valueOf(ts));
        Thread.sleep(1000);
        System.out.println(System.currentTimeMillis());*/
        /*Random random = new Random();
        RedisCluster redis = new RedisCluster();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {*/
            /*User user = new User();
            user.setUid(new Integer(i).longValue());
            user.setHeadUrl("rtyuikfbxv bhhidjoakfsxjguiokdlamfx");
            user.setUsername("jsfkljdsklfjslk");
            user.setFollowCount(1212);
            user.setFollowerCount(123213);
            user.setActivateCode("fjksdfds");
            user.setJoinTime("789789e789789e");
            user.setJob("fiodsufioudsiof");*/
        // JSONObject json = JSONObject.fromObject(user);//将java对象转换为json对象
        //redis.hset(RedisKey.USER_ALL_LIST,user.getUid().toString(),json.toString());
        //System.out.println(json.toString());
        //redis.hdel(RedisKey.USER_ALL_LIST,String.valueOf(i));

            /*redis.set("incrTest_"+i,0);
            redis.hset("incrTestHash",String.valueOf(i),0);*/


        //Object s = redis.get("incrTest_"+String.valueOf(random.nextInt(50000)%(50000-1+1)+1));
        //String s = redis.hget("incrTestHash",String.valueOf(random.nextInt(50000)%(50000-1+1)+1));
        //redis.set("incrTest_"+String.valueOf(random.nextInt(50000)%(50000-1+1)+1),"0");
        //redis.hset("incrTestHash",String.valueOf(random.nextInt(50000)%(50000-1+1)+1),0);

            /*redis.del("incrTest_" +i);
            redis.hdel("incrTestHash",String.valueOf(i));*/


        /*long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);*/

       /* User user = new User();
        user.setUid(1L);
        user.setHeadUrl("rtyuikfbxv bhhidjoakfsxjguiokdlamfx");
        user.setUsername("jsfkljdsklfjslk");
        user.setFollowCount(1212);
        user.setFollowerCount(123213);
        user.setActivateCode("fjksdfds");
        user.setJoinTime("789789e789789e");
        user.setJob("fiodsufioudsiof");
        JSONObject json = JSONObject.fromObject(user);//将java对象转换为json对象
        User user1 = (User) JSONObject.toBean(json,User.class);
        System.out.println(user1.getUid());
        System.out.println(json);*/

        Jedis jedis = new Jedis("47.94.158.71", 7379);
        jedis.auth("buzhidao");


        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 200000; i++) {
            User user = new User();
            user.setUid(new Integer(i).longValue());
            user.setHeadUrl("https://t1.hddhhn.com/uploads/tu/201901/306/1.jpg");
            user.setUsername("刚天村正图一");
            user.setFollowCount(1212);
            user.setFollowerCount(123213);
            user.setActivateCode("fjksdfds");
            user.setJoinTime("789789e789789e");
            user.setJob("fiodsufioudsiof");
            user.setDescription("Java程序员的自我修养，Java程序员的自我修养，Java程序员的自我修养，Java程序员的自我修养，Java程序员的自我修养，Java程序员的自我修养，Java程序员的自我修养，Java程序员的自我修养");
            //jedis.set("userInfo".getBytes(),SerializeUtil.serialize(user));
            //User user1 = (User) SerializeUtil.unserialize(jedis.get("userInfo".getBytes()));
            /*byte[] bytes = SerializeUtil.serialize(user);
            User user1 = (User) SerializeUtil.unserialize(bytes);*/
            JSONObject jsonObject = JSONObject.fromObject(user);
            User user1 = (User) JSONObject.toBean(jsonObject,User.class);

        }

        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);


    }

}
