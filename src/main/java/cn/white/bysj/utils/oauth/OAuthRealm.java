/*
package cn.white.bysj.utils.oauth;

//import com.shimh.common.constant.Base;
//import com.shimh.entity.User;
//import com.shimh.entity.UserStatus;
//import com.shimh.service.UserService;
import cn.white.bysj.user.User;
import cn.white.bysj.user.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

*/
/**
 * 自定义shiroRealm
 * 
 * @author white
 *
 * 2018年3月16日
 *
 *//*

public class OAuthRealm extends AuthorizingRealm {
	
	@Autowired
	private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String account = (String)principals.getPrimaryPrincipal();
        User user = userService.checkEmailIsExist(account);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<String>();
        
        //简单处理   只有admin一个角色
        if(user.getAdmin()){
        	roles.add(Base.ROLE_ADMIN);
        }
        
        authorizationInfo.setRoles(roles);

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String account = (String)token.getPrincipal();

        User user = userService.getUserByAccount(account);

        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        if(UserStatus.blocked.equals(user.getStatus())) {
            throw new LockedAccountException(); //帐号锁定
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getAccount(), 
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName()  
        );
        
        return authenticationInfo;
    }

}
*/
