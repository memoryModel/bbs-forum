package com.fc.controller;

import com.fc.model.Topic;
import com.fc.service.ReplyService;
import com.fc.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    /**
     * 对于发帖的回复
     * @param pid
     * @param content
     * @param session
     * @return
     */
    @RequestMapping("/reply.do")
    public String reply(Long pid, String content, HttpSession session){
        Integer sessionUid = (Integer) session.getAttribute("uid");
        replyService.reply(sessionUid.longValue(),pid,content);
        return "redirect:toPost.do?pid="+pid;
    }

    /**
     * 对于发帖回复的回复
     * @param pid
     * @param rid
     * @param content
     * @param session
     * @return
     */
    @RequestMapping("/comment.do")
    public String comment(Long pid,int rid, String content, HttpSession session){
        Integer sessionUid = (Integer) session.getAttribute("uid");
        replyService.comment(pid,sessionUid.longValue(),rid,content);
        return "redirect:toPost.do?pid="+pid;
    }
}





