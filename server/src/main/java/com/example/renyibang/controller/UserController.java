package com.example.renyibang.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.util.ResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/login")
    public JSONObject login(String name, String password){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        try{
            subject.login(token);//执行登录，如果没有异常说明正常
            return ResponseUtil.success("登录成功");
        } catch (UnknownAccountException e){
            return ResponseUtil.error("用户名不存在");
        } catch (IncorrectCredentialsException e){
            return ResponseUtil.error("密码错误");
        } catch (AuthenticationException e){
            return ResponseUtil.error("其他错误");
        }
    }
}
