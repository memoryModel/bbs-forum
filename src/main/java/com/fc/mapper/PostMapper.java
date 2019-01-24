package com.fc.mapper;

import com.fc.model.Post;
import com.fc.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PostMapper {


    List<Post> listPostByUid(Long uid);

    int insertPost(Post post);

    List<Post> listPostByTime(@Param("offset") int offset, @Param("limit") int limit);

    int selectPostCount();

    Post getPostByPid(Long pid);

    void updateReplyCount(Long pid);

    void updateScanCount(Long pid);

    void updateReplyTime(Long pid);

    int getUidByPid(Long pid);

    String getTitleByPid(Long pid);

    /**
     * 根据用户id查询所有发帖id
     * @param uid
     * @return
     */
    List<String> getPostListByUid(Long uid);
}
