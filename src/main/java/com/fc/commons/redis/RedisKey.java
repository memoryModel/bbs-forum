package com.fc.commons.redis;

/**
 * Created by wanghw on 2017/6/1.
 */
public class RedisKey {
    /**
     * 帖子点赞数key：set
     * 存储人员id
     */
    public final static String POST_LIKE = "post_like_";

    /**
     * 帖子点赞列表：zset
     */
    public final static String POST_LIKE_LIST = "post_like_list_";

    /**
     * 帖子详情：hash
     */
    public final static String POST_INFO = "post_info_";

    /**
     * 用户详情：hash
     */
    public final static String USER_INFO = "user_info_";

    /**
     * 最近的帖子列表（创建时间最大的）：zset
     */
    public final static String RECENTLY_POST_LIST = "recently_post_list";

    /**
     * 最热的帖子列表(回复数量最多的)：zset
     */
    public final static String LIVELY_POST_LIST = "lively_post_list";

    /**
     * 精华的帖子列表（点赞数量最多的）：zset
     */
    public final static String ESSENCE_POST_LIST = "essence-post_list";

    /**
     * 关注自己的用户列表：set
     */
    public final static String ATTENTION_USER_LIST = "attention_user_list_";

    /**
     * 自己关注的用户列表：set
     */
    public final static String USER_ATTENTION_LIST = "user_attention_list_";

    /**
     * 浏览自己主页的用户列表：set
     */
    public final static String BROWSE_HOME_LIST = "browse_home_list_";

    /**
     * 帖子的浏览用户：set
     */
    public final static String POST_BROWSE_LIST = "post_browse_list_";

    /**
     * 用户的发帖量：set
     */
    public final static String USER_POST_NUMBER = "user_post_number_list_";

    /**
     * 近期活跃的用户列表
     */
    public final static String ACTIVE_USER_LIST = "active_user_list";

    /**
     * 近期加入的用户列表
     */
    public final static String RECENT_USER_LIST = "recent_user_list";

    /**
     * 整个用户的列表：hash id做标示
     */
    public final static String USER_ALL_LIST = "user_all_list";

    /**
     * 用户的获赞、发贴、个人主页被浏览数：hash  key的标示为id
     */
    public final static String USER_SIGNIFICANCE_INFO = "user_significance_info_";

    /**
     * 帖子回复一级列表 list
     */
    public final static String POST_STAIR_REPLY_LIST = "post_stair_reply_list_";

    /**
     * 回复评论列表 list
     */
    public final static String REPLY_COMMENT_LIST = "reply_comment_list_";
}
