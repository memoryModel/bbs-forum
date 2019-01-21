package com.fc.service;

import com.fc.async.MailTask;
import com.fc.commons.redis.RedisCluster;
import com.fc.commons.redis.RedisKey;
import com.fc.commons.util.DateUtils;
import com.fc.commons.util.IdWorker;
import com.fc.mapper.UserMapper;
import com.fc.model.User;
import com.fc.commons.util.MyConstant;
import com.fc.commons.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LoginService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private RedisCluster redis;

    //注册
    public String register(User user,String repassword) {

        //校验邮箱格式
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
        Matcher m = p.matcher(user.getEmail());
        /*if(!m.matches()){
            return "邮箱格式有问题啊~";
        }*/

        //校验密码长度
        p = Pattern.compile("^\\w{6,20}$");
        m = p.matcher(user.getPassword());
        if(!m.matches()){
            return "密码长度要在6到20之间~";
        }

        //检查密码
        if(!user.getPassword().equals(repassword)){
            return "两次密码输入不一致~";
        }

        //检查邮箱是否被注册
        int emailCount = userMapper.selectEmailCount(user.getEmail());
        if(emailCount>0){
            return "该邮箱已被注册~";
        }

        //构造user，默认激活
        user.setActived(1);
        String activateCode = MyUtil.createActivateCode();
        user.setActivateCode(activateCode);
        user.setJoinTime(DateUtils.formatDateTime(new Date()));
        user.setUsername("DF"+new Random().nextInt(10000)+"号");
        user.setHeadUrl(userMapper.randomMessage());
        try {
            user.setUid(IdWorker.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO:发送邮件
        //taskExecutor.execute(new MailTask(activateCode,user.getEmail(),javaMailSender,1));

        //向数据库插入记录
        userMapper.insertUser(user);

        // 将用户名、头像加入redis缓存中
        redis.hset(RedisKey.USER_INFO+user.getUid(),"userName",user.getUsername());
        redis.hset(RedisKey.USER_INFO+user.getUid(),"headUrl",user.getHeadUrl());

        // 加入最近创建的用户zset集合中
        redis.zadd(RedisKey.RECENT_USER_LIST,System.currentTimeMillis(),user.getUid()+"");

        return "ok";
    }

    /**
     * 登录
     * @param user
     * @return
     */
    public Map<String,Object> login(User user) {

        Map<String,Object> map = new HashMap<>();
        Long uid = userMapper.selectUidByEmailAndPassword(user);
        if(uid==null){
            map.put("status","no");
            map.put("error","用户名或密码错误~");
            return map;
        }

        int checkActived = userMapper.selectActived(user);
        if(checkActived==0){
            map.put("status","no");
            map.put("error","您还没有激活账户哦，请前往邮箱激活~");
            return map;
        }

        String headUrl = userMapper.selectHeadUrl(uid);

        map.put("status","yes");
        map.put("uid",uid);
        map.put("headUrl",headUrl);
        return map;
    }

    public void activate(String activateCode) {
        userMapper.updateActived(activateCode);
    }
}
