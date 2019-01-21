package com.fc.service;

import com.fc.async.MailTask;
import com.fc.commons.redis.RedisCluster;
import com.fc.commons.redis.RedisKey;
import com.fc.mapper.UserMapper;
import com.fc.model.Info;
import com.fc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.*;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private RedisCluster redis;

    public User getProfile(Long sessionUid, Long uid) {
        //如果是浏览别人的主页，则增加主页浏览数
        if(sessionUid!=uid){
            userMapper.updateScanCount(uid);
        }
        //从数据库得到User对象
        User user = userMapper.selectUserByUid(uid);
        //设置获赞数，关注数，粉丝数
        user.setFollowCount((int)(long)redis.scard(uid+":follow"));
        user.setFollowerCount((int)(long)redis.scard(uid+":fans"));
        String likeCount = redis.hget("vote",uid+"");
        if(likeCount==null){
            user.setLikeCount(0);
        }else {
            user.setLikeCount(Integer.valueOf(likeCount));
        }
        return user;
    }

    public User getEditInfo(int uid) {
        return userMapper.selectEditInfo(uid);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public void record(StringBuffer requestURL, String contextPath, String remoteAddr) {
        Info info = new Info();
        info.setRequestUrl(requestURL.toString());
        info.setContextPath(contextPath);
        info.setRemoteAddr(remoteAddr);
        userMapper.insertInfo(info);
    }

    public List<User> listUserByTime() {
        Set<String> set = redis.zrevrange(RedisKey.RECENT_USER_LIST,0,5);
        if (set == null || set.isEmpty()) return userMapper.listUserByTime();
        List<User> users = new LinkedList<>();

        for (String str : set) {
            User user = new User();
            user.setUsername(redis.hget(RedisKey.USER_INFO+str,"userName"));
            user.setHeadUrl(redis.hget(RedisKey.USER_INFO+str,"headUrl"));
            users.add(user);
        }
        return users;
    }

    public List<User> listUserByHot() {
        return userMapper.listUserByHot();
    }

    public void updateHeadUrl(int uid, String headUrl) {
        userMapper.updateHeadUrl(uid,headUrl);
    }

    public void unfollow(int sessionUid, int uid) {
        /*Transaction tx = redis.multi();
        tx.srem(sessionUid+":follow", String.valueOf(uid));
        tx.srem(uid+":fans", String.valueOf(sessionUid));
        tx.exec();*/
    }

    public void follow(int sessionUid, int uid) {
        /*Transaction tx = redis.multi();
        tx.sadd(sessionUid+":follow", String.valueOf(uid));
        tx.sadd(uid+":fans", String.valueOf(sessionUid));
        tx.exec();
        if(jedis!=null){
            jedisPool.returnResource(jedis);
        }*/
    }

    public boolean getFollowStatus(Long sessionUid, Long uid) {
       /* Jedis jedis = jedisPool.getResource();
        boolean following = jedis.sismember(sessionUid+":follow", String.valueOf(uid));
        if(jedis!=null){
            jedisPool.returnResource(jedis);
        }
        return following;*/
       return true;
    }

    public String updatePassword(String password, String newpassword, String repassword, int sessionUid) {

        String oldPassword = userMapper.selectPasswordByUid(sessionUid);
        if(!oldPassword.equals(password)){
            return "原密码输入错误~";
        }

        if(newpassword.length()<6 ||newpassword.length()>20){
            return "新密码长度要在6~20之间~";
        }

        if(!newpassword.equals(repassword)){
            return "新密码两次输入不一致~";
        }

        userMapper.updatePassword(newpassword,sessionUid);
        return "ok";
    }

    //发送忘记密码确认邮件
    public void forgetPassword(String email) {
        String verifyCode = userMapper.selectVerifyCode(email);
        System.out.println("verifyCode:"+verifyCode);
        //发送邮件
        taskExecutor.execute(new MailTask(verifyCode,email,javaMailSender,2));
    }

    public void verifyForgetPassword(String code) {
        System.out.println("更新前："+code);
        userMapper.updatePasswordByActivateCode(code);
        System.out.println("更新后："+code);
    }

    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }
}

