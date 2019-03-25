package com.fc.controller;


import com.fc.model.User;
import com.fc.service.LoginService;
import com.fc.TransactionTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Demo class
 *
 * @author mac
 * @date 2016/10/31
 */
@Controller
@RequestMapping("/")
public class LoginController {


    @Autowired
    private LoginService loginService;

    @Autowired
    private TransactionTest transactionTest;


    /**
     * 去注册和登录的页面
     * @return
     */
    @RequestMapping({"/toLogin.do","/"})
    public String toLogin(){
        return "login";
    }

    /**
     * 注册
     * @param user
     * @param repassword
     * @param model
     * @return
     */
    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    public String register(User user, String repassword,Model model){
        String result = loginService.register(user,repassword);
        if(result.equals("ok")){
            model.addAttribute("info","系统已经向你的邮箱发送了一封邮件哦，验证后就可以登录啦~");
            return "prompt/promptInfo";
        }else {
            model.addAttribute("register","yes");
            model.addAttribute("email",user.getEmail());
            model.addAttribute("error",result);
            return "login";
        }
    }

    /**
     * 登录
     * @param user
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    public String login(User user,Model model,HttpSession session){
        Map<String,Object> map = loginService.login(user);

        if(map.get("status").equals("yes")){
            session.setAttribute("uid",map.get("uid"));
            session.setAttribute("headUrl",map.get("headUrl"));
            return "redirect:toMyProfile.do";
        }else {
            model.addAttribute("email",user.getEmail());
            model.addAttribute("error",map.get("error"));
            return "login";
        }
    }

    /**
     * 激活
     * @param code
     * @param model
     * @return
     */
    @RequestMapping(value = "/activate.do")
    public String activate(String code,Model model){
        loginService.activate(code);
        loginService.toString();


        model.addAttribute("info","您的账户已经激活成功，可以去登录啦~");
        return "prompt/promptInfo";
    }

    /**
     * 注销注销可能是的吧
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout.do",method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("uid");
        return "redirect:toIndex.do";
    }

    @Deprecated
    @RequestMapping("/testPool.do")
    public @ResponseBody String test(HttpServletRequest request) {

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 10000; i++) {
            executorService.execute(() -> {
                System.out.println("当前执行线程:" + Thread.currentThread().getName());
            });
        }

        return "success";
    }

    @RequestMapping("/test1.do")
    public @ResponseBody String testSynchronized() {

        for (int i = 0; i < 800; i++) {
            /*new Thread(() -> {
                try {
                    loginService.updateLikeCount();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();*/

        }

        loginService.updateTest();

        //transactionTest.test();
        return "success";
    }

    @RequestMapping("/test2.do")
    public @ResponseBody String test2() {

        //loginService.updateTest();

        transactionTest.saveTest();
        return "success";
    }

    @RequestMapping("/test3.do")
    public @ResponseBody String test3() {

        loginService.updateLikeCount();
        return "success";
    }
}


