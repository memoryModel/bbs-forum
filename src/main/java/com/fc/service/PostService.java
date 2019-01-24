package com.fc.service;

import com.fc.async.MessageTask;
import com.fc.commons.redis.RedisCluster;
import com.fc.commons.redis.RedisKey;
import com.fc.commons.status.PostListType;
import com.fc.commons.util.IdWorker;
import com.fc.mapper.MessageMapper;
import com.fc.mapper.PostMapper;
import com.fc.mapper.ReplyMapper;
import com.fc.mapper.UserMapper;
import com.fc.model.PageBean;
import com.fc.model.Post;
import com.fc.commons.util.DateUtils;
import com.fc.commons.util.MyConstant;
import com.fc.commons.util.MyUtil;
import com.fc.model.User;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;


@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private RedisCluster redis;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private UserService userService;

    /**
     * 根据uid，获得帖子列表
     * @param uid
     * @return
     */
    public List<Post> getPostList(Long uid) {
        return postMapper.listPostByUid(uid);
    }

    /**
     * 发帖
     * @param post
     * @return
     */
    public Long publishPost(Post post) {
        // 构造帖子
        post.setPublishTime(DateUtils.formatDateTime(new Date())); // 发布时间
        post.setReplyTime(DateUtils.formatDateTime(new Date())); // 最后回复
        post.setReplyCount(0);
        post.setLikeCount(0);
        post.setScanCount(0);
        try {
            post.setPid(IdWorker.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //插入一条帖子记录
        postMapper.insertPost(post);

        //更新用户发帖量
        userMapper.updatePostCount(post.getUser().getUid());
        redis.hincrBy(RedisKey.USER_SIGNIFICANCE_INFO+post.getUser().getUid(),"postCount",1);

        // 将帖子相关详情添加到redis做缓存
        Map<String, String> postMap = new HashMap<>();
        postMap.put("title",post.getTitle());
        postMap.put("pid", String.valueOf(post.getPid()));
        postMap.put("content", post.getContent());
        postMap.put("publishTime", post.getPublishTime());
        postMap.put("replyTime", post.getReplyTime());
        postMap.put("replyCount", post.getReplyCount()+"");
        postMap.put("likeCount", post.getLikeCount()+"");
        postMap.put("scanCount", post.getScanCount()+"");
        postMap.put("uid", post.getUser().getUid()+"");
        redis.hmset(RedisKey.POST_INFO+post.getPid(), postMap);

        // 最近
        redis.zadd(RedisKey.RECENTLY_POST_LIST,System.currentTimeMillis(),String.valueOf(post.getPid()));
        // 最热
        redis.zadd(RedisKey.LIVELY_POST_LIST,0,post.getPid().toString());
        // 精华
        redis.zadd(RedisKey.ESSENCE_POST_LIST,0,post.getPid().toString());

        return post.getPid();
    }

    /**
     * 根据条件列出帖子
     * @param curPage
     * @param typeFlag
     * @return
     */
    public PageBean<Post> listPostByTime(int curPage, int typeFlag) {
        //每页记录数，从哪开始
        int limit = 8;
        int offset = (curPage-1) * limit;
        //获得总记录数，总页数
        //int allCount = postMapper.selectPostCount();
        int allCount = Math.toIntExact(redis.zcard(RedisKey.RECENTLY_POST_LIST));
        int allPage = 0;
        if (allCount <= limit) {
            allPage = 1;
        } else if (allCount / limit == 0) {
            allPage = allCount / limit;
        } else {
            allPage = allCount / limit + 1;
        }

        //构造PageBean
        PageBean<Post> pageBean = new PageBean<>(allPage,curPage);

        //分页得到数据列表
        Set<String> set = redis.zrevrange(PostListType.postListTypeName(typeFlag),offset,limit);
        if (set == null || set.isEmpty()) {
            List<Post> postList = postMapper.listPostByTime(offset,limit);
            pageBean.setList(postList);
            return pageBean;
        }
        List<Post> postList = new LinkedList<>();
        for (String str : set) {
            Map<String, String> map = redis.hgetall(RedisKey.POST_INFO+str);
            Post post = new Post();
            post.setPid(Long.parseLong(map.get("pid")));
            post.setTitle(map.get("title"));
            post.setContent(map.get("content"));
            post.setPublishTime(map.get("publishTime"));
            post.setReplyTime(map.get("replyTime"));
            post.setLikeCount(Integer.valueOf(map.get("likeCount")));
            post.setReplyCount(Integer.valueOf(map.get("replyCount")));
            post.setScanCount(Integer.valueOf(map.get("scanCount")));
            // 因论坛中用户名以及头像的出现频率最高。所以加入缓存
            String userStr = redis.hget(RedisKey.USER_ALL_LIST,map.get("uid"));
            JSONObject jsonObject=JSONObject.fromObject(userStr);
            User user = (User) JSONObject.toBean(jsonObject, User.class);

            /*Map<String, String> userMap = redis.hgetall(RedisKey.USER_INFO+map.get("uid"));
            User user = new User(Long.parseLong(map.get("uid")),userMap.get("userName"),userMap.get("headUrl"));*/
            post.setUser(user);
            postList.add(post);
        }
        // 获取点赞数
        for(Post post : postList){
            post.setLikeCount((int)(long)redis.scard(RedisKey.POST_LIKE+post.getPid()));
        }

        pageBean.setList(postList);

        return pageBean;
    }

    /**
     * 更新帖子的浏览数
     * @param pid
     * @param sessionUid
     * @return
     */
    public Post getPostByPid(Long pid, Long sessionUid) {
        // 判断此用户是否浏览过此帖子 已浏览将不进行浏览数+1等操作
        boolean isScan = getScanStatus(pid, String.valueOf(sessionUid));
        if (!isScan) {
            // 更新浏览数
            postMapper.updateScanCount(pid);
            // 更新redis中帖子浏览量
            redis.hincrBy(RedisKey.POST_INFO+pid,"scanCount",1);
            // 添加用户到帖子的已浏览用户set
            redis.sadd(RedisKey.POST_BROWSE_LIST+pid,sessionUid.toString());
        }
        Map<String, String> map = redis.hgetall(RedisKey.POST_INFO+pid);
        Post post = new Post();
        post.setPid(Long.parseLong(map.get("pid")));
        post.setTitle(map.get("title"));
        post.setContent(map.get("content"));
        post.setPublishTime(map.get("publishTime"));
        post.setReplyTime(map.get("replyTime"));
        post.setLikeCount(Integer.valueOf(map.get("likeCount")));
        post.setReplyCount(Integer.valueOf(map.get("replyCount")));
        post.setScanCount(Integer.valueOf(map.get("scanCount")));
        // 因论坛中用户名以及头像的出现频率最高。所以加入缓存
        String userStr = redis.hget(RedisKey.USER_ALL_LIST,sessionUid.toString());
        JSONObject jsonObject=JSONObject.fromObject(userStr);
        User user = (User) JSONObject.toBean(jsonObject, User.class);
        //Map<String, String> userMap = redis.hgetall(RedisKey.USER_INFO+map.get("uid"));
        //User user = new User(Long.parseLong(map.get("uid")),userMap.get("userName"),userMap.get("headUrl"));
        post.setUser(user);


        //设置点赞数
        /*long likeCount = redis.scard(RedisKey.POST_LIKE+pid);
        post.setLikeCount((int)likeCount);*/

        return post;
    }

    /**
     * 点赞
     * @param pid
     * @param sessionUid
     * @return
     */
    public String clickLike(Long pid, Long sessionUid, String postUserId) {
        boolean ifLike = getLikeStatus(pid, String.valueOf(sessionUid));
        if (!ifLike) {
            // 帖子被sessionUid点赞
            redis.sadd(RedisKey.POST_LIKE+pid, String.valueOf(sessionUid));
            // 增加帖子获赞数
            redis.hincrBy(RedisKey.POST_INFO+pid,"likeCount",1);
            // 精华帖子集合中增加点赞量
            redis.zincrby(RedisKey.ESSENCE_POST_LIST,1D,pid.toString());
            // 用户增加1点获赞数 得到发帖人 点赞+1
            /*String uid = redis.hget(RedisKey.POST_INFO+pid,"uid");
            userMapper.updateLikeCountByUid(Long.parseLong(uid));*/
            redis.hincrBy(RedisKey.USER_SIGNIFICANCE_INFO+postUserId,"likeCount",1);
        }

        //TODO:插入一条点赞消息
        //taskExecutor.execute(new MessageTask(messageMapper,userMapper,postMapper,replyMapper,pid,0,sessionUid, MyConstant.OPERATION_CLICK_LIKE));
        String result = String.valueOf(redis.scard(RedisKey.POST_LIKE+pid));

        return result;
    }

    /**
     * 某用户是否赞过某帖子
     * @param pid
     * @param sessionUid
     * @return
     */
    public boolean getLikeStatus(Long pid, String sessionUid) {
        boolean result = redis.sismember(RedisKey.POST_LIKE+pid, sessionUid);
        return result;
    }

    /**
     * 某用户是否浏览过某帖子
     * @param pid
     * @param sessionUid
     * @return
     */
    public boolean getScanStatus(Long pid, String sessionUid) {
        boolean result = redis.sismember(RedisKey.POST_BROWSE_LIST+pid, sessionUid);
        return result;
    }

    /**
     * 根据用户id查询所有发帖id
     * @param uid
     * @return
     */
    public List<String> getPostListByUid(Long uid) {
        return  postMapper.getPostListByUid(uid);
    }
}

