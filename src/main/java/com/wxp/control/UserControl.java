package com.wxp.control;

import com.wxp.bean.User;
import com.wxp.dao.PrizeCodeDao;
import com.wxp.service.PrizeCodeService;
import com.wxp.service.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * create by ff on 2018/6/23
 */
@Controller
@ResponseBody
@RequestMapping("/ss")
public class UserControl {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Resource
    private UserService userService;
    @Autowired
    private PrizeCodeService prizeCodeService;

    @RequestMapping(value = "/subLogin",method = RequestMethod.POST)
    @ResponseBody
    public Object subLogin(User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "登录成功";
    }

    @RequestMapping(value = "/regist",method = RequestMethod.POST)
    @ResponseBody
    public Object regist(User user){
        try {
            userService.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "成功";
    }

    @RequestMapping("/tt")
    public Object login(){
        return "tt";
    }

    @RequestMapping(value = "/dex")
    @ResponseBody
    public Object insert(){
        try {
            prizeCodeService.insertttt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "成功";
    }

}
