package com.fc.service;

import com.fc.async.MailTask;
import com.fc.commons.redis.RedisCluster;
import com.fc.commons.redis.RedisKey;
import com.fc.mapper.UserMapper;
import com.fc.model.Info;
import com.fc.model.NetworkNode;
import com.fc.model.User;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    private PostService postService;

    public User getProfile(Long sessionUid, Long uid) {
        // 如果是浏览别人的主页，则增加主页浏览数
        if(sessionUid!=uid && !ifBrowseHome(sessionUid.toString(),uid.toString())){
            redis.sadd(RedisKey.BROWSE_HOME_LIST+uid,sessionUid.toString());
            redis.hincrBy(RedisKey.USER_SIGNIFICANCE_INFO+uid,"scanCount",1);
            //userMapper.updateScanCount(uid);
        }
        // 从数据库得到User对象
        //User user1 = userMapper.selectUserByUid(uid);
        String userStr = redis.hget(RedisKey.USER_ALL_LIST,sessionUid.toString());
        JSONObject jsonObject=JSONObject.fromObject(userStr);
        User user = (User) JSONObject.toBean(jsonObject, User.class);
        Map<String, String> map = null;
        if (sessionUid != uid) {
            // 关注了
            user.setFollowCount(redis.scard(RedisKey.USER_ATTENTION_LIST+uid).intValue());
            // 关注者
            user.setFollowerCount(redis.scard(RedisKey.ATTENTION_USER_LIST+uid).intValue());
            map = redis.hgetall(RedisKey.USER_SIGNIFICANCE_INFO+uid);
        } else {
            // 关注了
            user.setFollowCount(redis.scard(RedisKey.USER_ATTENTION_LIST+sessionUid).intValue());
            // 关注者
            user.setFollowerCount(redis.scard(RedisKey.ATTENTION_USER_LIST+sessionUid).intValue());

            map = redis.hgetall(RedisKey.USER_SIGNIFICANCE_INFO+sessionUid);
        }

        user.setLikeCount(Integer.valueOf(map.get("likeCount")));
        user.setScanCount(Integer.valueOf(map.get("scanCount")));
        user.setPostCount(Integer.valueOf(map.get("postCount")));

        return user;
    }

    public User getEditInfo(Long uid) {
        return userMapper.selectEditInfo(uid);
    }

    /**
     * 修改用户基本信息
     * @param user
     */
    public void updateUser(User user) {
        userMapper.updateUser(user);
        redis.hset(RedisKey.USER_INFO+user.getUid(),"userName",user.getUsername());
    }

    public void record(StringBuffer requestURL, String contextPath, String remoteAddr) {
        Info info = new Info();
        info.setRequestUrl(requestURL.toString());
        info.setContextPath(contextPath);
        info.setRemoteAddr(remoteAddr);
        userMapper.insertInfo(info);
    }

    /**
     * 近期加入的用户
     * @return
     */
    public List<User> listUserByTime() {
        List<User> users = new LinkedList<>();
        Set<String> set = redis.zrevrange(RedisKey.RECENT_USER_LIST,0,5);
        // redis中记录近期用户注册的zset为空 需重新加入缓存
        if (set == null || set.isEmpty()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            users = userMapper.listUserByTime();
            for (User user : users) {
                Date date = null;
                try {
                    date = simpleDateFormat.parse(user.getJoinTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long createTime = date.getTime();
                redis.zadd(RedisKey.RECENT_USER_LIST,createTime,user.getUid().toString());
            }
            return users;
        }

        // 遍历redis中的近期加入用户
        for (String str : set) {
            // 因论坛中用户名以及头像的出现频率最高。所以加入缓存
            String userStr = redis.hget(RedisKey.USER_ALL_LIST,str);
            JSONObject jsonObject=JSONObject.fromObject(userStr);
            User user = (User) JSONObject.toBean(jsonObject, User.class);
            users.add(user);
        }

        return users;
    }

    public List<User> listUserByHot() {
        return userMapper.listUserByHot();
    }

    /**
     * 修改头像
     * @param uid
     * @param headUrl
     */
    public void updateHeadUrl(int uid, String headUrl) {
        userMapper.updateHeadUrl(uid,headUrl);
    }

    public void unfollow(Long sessionUid, String uid) {
        // 从关注者的已关注用户中删除登录用户
        redis.srem(RedisKey.ATTENTION_USER_LIST+uid,sessionUid.toString());
        // 从登录用户的额已关注列表删除tid
        redis.srem(RedisKey.USER_ATTENTION_LIST+sessionUid,uid);
    }

    public void follow(Long sessionUid, String uid) {
        // 将自己添加至关注者的已关注列表
        redis.sadd(RedisKey.ATTENTION_USER_LIST+uid,sessionUid.toString());
        // 将关注人员添加至自己的关注列表
        redis.sadd(RedisKey.USER_ATTENTION_LIST+sessionUid,uid);
    }

    public boolean getFollowStatus(Long sessionUid, Long uid) {
        return redis.sismember(RedisKey.USER_ATTENTION_LIST+sessionUid, uid.toString());
    }

    /**
     * 修改密码
     * @param password
     * @param newpassword
     * @param repassword
     * @param sessionUid
     * @return
     */
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

    public boolean ifBrowseHome(String sessionUid, String uid) {
        return redis.sismember(RedisKey.BROWSE_HOME_LIST+uid, sessionUid);
    }

    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    public List<User> commonFollowList(Long sessionUid, String uid) {
        Set<String> set = redis.sinter(RedisKey.USER_ATTENTION_LIST+sessionUid.toString(),RedisKey.USER_ATTENTION_LIST+uid);
        List<User> list = new ArrayList<>();
        if (set == null || set.isEmpty()) return list;
        for (String str : set) {
            Map<String, String> userMap = redis.hgetall(RedisKey.USER_INFO+str);
            User user = new User(Long.parseLong(userMap.get("uid")),userMap.get("userName"),userMap.get("headUrl"));
            list.add(user);
        }
        return list;
    }
}

