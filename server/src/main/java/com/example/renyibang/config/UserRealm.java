package com.example.renyibang.config;

import com.example.renyibang.entity.UserAuth;
import com.example.renyibang.service.UserAuthService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserAuthService userAuthService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthenticationInfo");
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        String username = userToken.getUsername();
        String password = new String(userToken.getPassword()); // 获取用户输入的密码

        // 根据用户名查找用户认证信息
        UserAuth userAuth = userAuthService.findByUserId(Long.parseLong(username)).orElse(null);

        if (userAuth == null) {
            throw new UnknownAccountException("用户名不存在");
        }

        // 验证密码
        if (!password.equals(userAuth.getPassword())) {
            throw new IncorrectCredentialsException("密码错误");
        }

        // 密码认证，shiro做
        return new SimpleAuthenticationInfo(username, userAuth.getPassword(), getName());
    }
}
