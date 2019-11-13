package com.zhoushan.wenhua.shiro;

import com.zhoushan.wenhua.model.User;
import com.zhoushan.wenhua.service.MyService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;


public class UserRealm extends AuthorizingRealm {

    @Autowired
    private MyService myService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        String perms = user.getPerms();
        String[] ss = perms.split(",");
        simpleAuthorizationInfo.addStringPermission(perms);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     *
     * @param
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String name = usernamePasswordToken.getUsername();
        System.out.println(name);
        String password = usernamePasswordToken.getPassword().toString();
        User user = myService.selectUserByName(name);

        if (null == user) {
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}
