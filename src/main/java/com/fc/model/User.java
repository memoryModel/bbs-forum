package com.fc.model;


import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -2929063984063273706L;
    private Long uid;

    //系统属性
    private String email;
    private String password;
    private Integer actived;
    private String activateCode;
    private String joinTime;

    //用户属性
    private String username;
    private String description;
    private String headUrl;
    private String position;
    private String school;
    private String job;

    /**
     * 发帖
     */
    private Integer postCount;
    /**
     * 个人主页被浏览次数
     */
    private Integer scanCount;
    /**
     * 所有获赞
     */
    private Integer likeCount;
    /**
     * 关注了
     */
    private Integer followCount;
    /**
     * 关注者
     */
    private Integer followerCount;

    public User() {}

    public User(Long uid) {
        this.uid = uid;
    }

    public User(Long uid, String username, String headUrl) {
        this.uid = uid;
        this.username = username;
        this.headUrl = headUrl;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getActived() {
        return actived;
    }

    public void setActived(Integer actived) {
        this.actived = actived;
    }

    public String getActivateCode() {
        return activateCode;
    }

    public void setActivateCode(String activateCode) {
        this.activateCode = activateCode;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Integer getScanCount() {
        return scanCount;
    }

    public void setScanCount(Integer scanCount) {
        this.scanCount = scanCount;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", actived=" + actived +
                ", activateCode='" + activateCode + '\'' +
                ", joinTime='" + joinTime + '\'' +
                ", username='" + username + '\'' +
                ", description='" + description + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", position='" + position + '\'' +
                ", school='" + school + '\'' +
                ", job='" + job + '\'' +
                ", likeCount=" + likeCount +
                ", postCount=" + postCount +
                ", scanCount=" + scanCount +
                ", followCount=" + followCount +
                ", followerCount=" + followerCount +
                '}';
    }
}
