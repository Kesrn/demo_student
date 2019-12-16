/**
 * All rights Reserved, Designed By ZC.LangFang
 *
 * @Title: Authentication.java
 * @author: XueYang.Li
 * @date: 2019/3/1 9:47 AM
 * @version V1.0
 * @Copyright: 2019/3/1 INM Inc. All rights reserved.
 * 注意：本内容仅限于河北志晟信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.zcx.test.config.security;


import com.zcx.test.entity.User;
import com.zcx.test.service.LoginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * @Title: Authentication
 * @Package: com.zc.partymember.security
 * @Describe: 认证类
 * @author: XueYang.Li
 * @date: 2019/3/1 9:47 AM
 * @version: V1.0
 **/
public class Authentication extends AuthenticatingRealm {

    @Autowired
    private LoginService loginService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String username = token.getUsername();
        User user = loginService.selectByName(username);
        if (user == null || StringUtils.isEmpty(user.getUsername())) {
            throw new UnknownAccountException("用户不存在");
        }
//        if (Consts.USER_STATUS_LOCK.equals(user.getStatus())) {
//            throw new LockedAccountException("用户被锁定");
//        }
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());
        AuthenticationInfo authenticationInfo =
                new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
        return authenticationInfo;
    }

//    public static void main(String[] args) {
//        String md5 = "MD5";
//        String username = "zcx";
//        Object password = "admin";
//        String _salt = ByteSource.Util.bytes(username).toString();
//        Object salt = ByteSource.Util.bytes(_salt);
//        int hasIter = 1024;
//
//        Object result = new SimpleHash(md5, password, salt, hasIter);
////        _salt:存入到数据库中的盐值
//        System.out.println(_salt);
//        System.out.println(salt);
//        System.out.println(result);
//    }
}
