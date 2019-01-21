package com.fc.mapper;

import com.fc.model.Comment;
import com.fc.model.Reply;
import com.fc.model.Topic;

import java.util.List;


public interface ReplyMapper {

    void insertReply(Reply reply);

    List<Reply> listReply(Long pid);

    void insertComment(Comment comment);

    List<Comment> listComment(Integer rid);

    String getContentByRid(int rid);

}
