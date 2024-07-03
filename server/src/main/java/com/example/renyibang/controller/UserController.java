package com.example.renyibang.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.entity.RegisterRequest;
import com.example.renyibang.service.UserService;
import com.example.renyibang.util.ResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public JSONObject login(String name, String password) {
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        try {
            subject.login(token);//执行登录，如果没有异常说明正常
            return ResponseUtil.success("登录成功");
        } catch (UnknownAccountException e) {
            return ResponseUtil.error("用户名不存在");
        } catch (IncorrectCredentialsException e) {
            return ResponseUtil.error("密码错误");
        } catch (AuthenticationException e) {
            return ResponseUtil.error("其他错误");
        }
    }

    @GetMapping("/register")
    public JSONObject register(@RequestBody RegisterRequest registerRequest) {
        String password = registerRequest.getPassword();
        String nickname = registerRequest.getNickname();
        String intro = registerRequest.getIntro();
        String avatar = registerRequest.getAvatar();
        try {
            long userId = userService.register(password, nickname, intro, avatar);
            JSONObject data = new JSONObject();
            data.put("userId", userId);
            return ResponseUtil.success("注册成功", data);
        } catch (Exception e) {
            return ResponseUtil.error("注册失败: " + e.getMessage());
        }
    }

    @GetMapping("/profile/self")
    public JSONObject getSelfInfo(long userId)
    {
        //userId待替换
        return userService.getUserInfo(userId);
    }

    @GetMapping("/profile/{userId}")
    public JSONObject getUserInfo(@PathVariable long userId)
    {
        return userService.getUserInfo(userId);
    }

    @PostMapping("/profile/modify/self")
    public JSONObject modifyUserInfo(long userId, @RequestBody JSONObject body)
    {
        return userService.modifyUserInfo(userId, body);
    }
}
