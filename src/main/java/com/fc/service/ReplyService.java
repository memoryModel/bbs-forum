package com.fc.service;

import com.fc.async.MessageTask;
import com.fc.commons.redis.RedisCluster;
import com.fc.commons.redis.RedisKey;
import com.fc.commons.util.IdWorker;
import com.fc.mapper.MessageMapper;
import com.fc.mapper.PostMapper;
import com.fc.mapper.ReplyMapper;
import com.fc.mapper.UserMapper;
import com.fc.model.*;
import com.fc.commons.util.MyConstant;
import com.google.gson.JsonObject;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Service
public class ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private RedisCluster redis;

    /**
     * 回复
     * @param sessionUid
     * @param pid
     * @param content
     */
    public void reply(Long sessionUid, Long pid, String content) {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //构造Reply对象
        Reply reply =new Reply();
        reply.setUser(new User(sessionUid));
        reply.setPost(new Post(pid));
        reply.setContent(content);
        reply.setReplyTime(sdf.format(new Date()));
        try {
            reply.setRid(IdWorker.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //向reply表插入一条记录
        try {
            replyMapper.insertReply(reply);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 插入最右边  读取从左边读，保证先发先读
        redis.rpush(RedisKey.POST_STAIR_REPLY_LIST+pid,JSONObject.fromObject(reply).toString());

        //更新帖子的回复数
        postMapper.updateReplyCount(pid);

        //更新最后回复时间
        postMapper.updateReplyTime(pid);
        redis.hset(RedisKey.POST_INFO+pid,"replyTime",sdf.format(new Date()));

        // 将redis中的数据进行更新
        updateRedisInfo(pid);

        //插入一条回复消息
        taskExecutor.execute(new MessageTask(messageMapper,userMapper,postMapper,replyMapper,pid,0,sessionUid, MyConstant.OPERATION_REPLY));

    }

    /**
     * 评论
     * @param pid
     * @param sessionUid
     * @param rid
     * @param content
     */
    public void comment(Long pid,Long sessionUid, Long rid, String content) {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //构造Comment
        Comment comment = new Comment();
        comment.setUser(new User(sessionUid));
        comment.setReply(new Reply(rid));
        comment.setContent(content);
        comment.setCommentTime(sdf.format(new Date()));
        try {
            comment.setCid(IdWorker.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //插入一条评论
        replyMapper.insertComment(comment);

        redis.rpush(RedisKey.REPLY_COMMENT_LIST+rid,JSONObject.fromObject(comment).toString());

        //更新最后回复时间
        postMapper.updateReplyTime(pid);
        redis.hset(RedisKey.POST_INFO+pid,"replyTime",sdf.format(new Date()));

        // 将redis中的数据进行更新
        updateRedisInfo(pid);

        //TODO：插入一条评论消息
        //taskExecutor.execute(new MessageTask(messageMapper,userMapper,postMapper,replyMapper,pid,rid,sessionUid, MyConstant.OPERATION_COMMENT));

    }

    /**
     * 1、对帖子详情中的回复次数进行增加
     * 2、对最热帖子zset集合中的分数进行增加
     * @param pid
     */
    public void updateRedisInfo(Long pid) {
        redis.hincrBy(RedisKey.POST_INFO+pid,"replyCount",1);
        redis.zincrby(RedisKey.LIVELY_POST_LIST,1D,pid.toString());
    }

    /**
     * 根据pid列出回复
     * @param pid
     * @return
     */
    public List<Reply> listReply(Long pid) {
        List<Reply> replies = new LinkedList<>();
        JSONObject jsonObject = new JSONObject();
        //列出回复
        List<String> replyStrList = redis.lrange(RedisKey.POST_STAIR_REPLY_LIST+pid,0,-1);
        for (String str : replyStrList) {
            Reply reply = (Reply) JSONObject.toBean(JSONObject.fromObject(str),Reply.class);
            User user = (User) JSONObject.toBean(jsonObject.fromObject(redis.hget(RedisKey.USER_ALL_LIST,String.valueOf(reply.getUser().getUid()))),User.class);
            reply.setUser(user);
            replies.add(reply);
        }
        for(Reply reply : replies){
            List<Comment> comments = new LinkedList<>();
            //列出每条回复下的评论
            List<String> commentList = redis.lrange(RedisKey.REPLY_COMMENT_LIST+reply.getRid(),0,-1);
            for (String s : commentList) {
                Comment comm = (Comment) jsonObject.toBean(jsonObject.fromObject(s),Comment.class);
                User user = (User) JSONObject.toBean(jsonObject.fromObject(redis.hget(RedisKey.USER_ALL_LIST,String.valueOf(reply.getUser().getUid()))),User.class);
                comm.setUser(user);
                comments.add(comm);
            }
            reply.setCommentList(comments);
        }
        return replies;
    }
}

