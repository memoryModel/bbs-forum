package com.fc.service;

import com.fc.commons.redis.RedisCluster;
import com.fc.commons.redis.RedisKey;
import com.fc.commons.util.DateUtils;
import com.fc.commons.util.IdWorker;
import com.fc.mapper.UserMapper;
import com.fc.model.User;
import com.fc.commons.util.MyUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

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

    @Autowired
    private TransactionTemplate transactionTemplate;

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

        // 将用户信息存入缓存中
        JSONObject json = JSONObject.fromObject(user);//将java对象转换为json对象
        redis.hset(RedisKey.USER_ALL_LIST,user.getUid().toString(),json.toString());

        // 将用户易改动数据存入redis 被点赞总数、发帖、个人主页被浏览数
        Map<String, String> map = new HashMap<>();
        map.put("likeCount","0");
        map.put("postCount","0");
        map.put("scanCount","0");
        redis.hmset(RedisKey.USER_SIGNIFICANCE_INFO+user.getUid(),map);

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

        List<User> users = userMapper.getAllUser();
        for (User u: users) {
            JSONObject object = JSONObject.fromObject(u);
            redis.hset(RedisKey.USER_ALL_LIST,u.getUid().toString(),object.toString());
            Map<String, String> map = new HashMap<>();
            map.put("likeCount","0");
            map.put("scanCount","0");
            map.put("postCount","0");
            redis.hmset(RedisKey.USER_SIGNIFICANCE_INFO+u.getUid(),map);
        }

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

        String userStr = redis.hget(RedisKey.USER_ALL_LIST,String.valueOf(uid));
        JSONObject jsonObject=JSONObject.fromObject(userStr);
        user = (User) JSONObject.toBean(jsonObject, User.class);
        map.put("status","yes");
        map.put("uid",uid);
        map.put("headUrl",user.getHeadUrl());
        return map;
    }

    public void activate(String activateCode) {
        userMapper.updateActived(activateCode);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public  void updateLikeCount() {
        /*synchronized (this) {

        }*/

        User user = userMapper.getUserByUid(604779243695509504L);
        user.setLikeCount(user.getLikeCount() + 1);
        userMapper.updateLikeCount(user);
    }

    public void updateTest() {

        /*transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                User user = userMapper.getUserByUid(604779243695509504L);
                user.setLikeCount(user.getLikeCount() + 1);
                userMapper.updateLikeCount(user);

                int i = 1/0;

                System.out.println("哈哈");

                userMapper.updateScanCount(604779243695509504L);
            }
        });*/

        User user = userMapper.getUserByUid(604779243695509504L);
        user.setLikeCount(user.getLikeCount() + 1);
        userMapper.updateLikeCount(user);

        int i = 1/0;

        System.out.println("哈哈");

        userMapper.updateScanCount(604779243695509504L);



    }
}
