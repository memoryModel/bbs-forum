package com.fc.model;

import java.io.Serializable;
import java.util.List;

/**
 * 答复
 */
public class Reply implements Serializable {

    private static final long serialVersionUID = 3691478550106000423L;
    private Long rid;
    /**
     * 回帖内容
     */
    private String content;
    /**
     * 帖子
     */
    private Post post;
    /**
     * 回帖用户
     */
    private User user;
    /**
     * 回复时间
     */
    private String replyTime;
    /**
     * 存储楼中楼的集合
     */
    private List<Comment> commentList;

    public Reply() {}

    public Reply(Long rid) {
        this.rid = rid;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "rid=" + rid +
                ", content='" + content + '\'' +
                ", post=" + post +
                ", user=" + user +
                ", replyTime='" + replyTime + '\'' +
                ", commentList=" + commentList +
                '}';
    }
}
