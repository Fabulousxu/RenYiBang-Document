package com.example.renyibang.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.entity.RegisterRequest;
import com.example.renyibang.service.UserService;
import com.example.renyibang.util.JwtUtil;
import com.example.renyibang.util.ResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {
  @Autowired private UserService userService;

  @PostMapping("/login")
  public JSONObject login(String name, String password) {
    // 获取当前用户
    Subject subject = SecurityUtils.getSubject();
    // 封装登录数据
    UsernamePasswordToken token = new UsernamePasswordToken(name, password);
    try {
      subject.login(token); // 执行登录，如果没有异常说明正常
      System.out.println("当前登录用户：" + SecurityUtils.getSubject().getPrincipal());
      String jwt = JwtUtil.createJWT(name);
      JSONObject data = new JSONObject();
      data.put("jwt", jwt);
      return ResponseUtil.success("登录成功", data);
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
    System.out.println("register");

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

  @PostMapping("/logout")
  public JSONObject logout() {
    Subject subject = SecurityUtils.getSubject();
    System.out.println("当前要被注销的登录用户：" + subject.getPrincipal());
    subject.logout(); // 执行注销
    System.out.println("用户已注销");
    return ResponseUtil.success("注销成功");
  }
}
