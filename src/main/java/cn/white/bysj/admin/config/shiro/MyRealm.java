package cn.white.bysj.admin.config.shiro;

import cn.white.bysj.admin.common.utils.MD5Utils;
import cn.white.bysj.admin.entity.Resource;
import cn.white.bysj.admin.entity.Role;
import cn.white.bysj.user.User;
import cn.white.bysj.admin.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author SPPan
 *
 */
@Component
public class MyRealm extends AuthorizingRealm {

	public MyRealm(){
		super(new AllowAllCredentialsMatcher());
        setAuthenticationTokenClass(UsernamePasswordToken.class);

        //FIXME: 暂时禁用Cache
        setCachingEnabled(false);
	}
	
	@Autowired
	private IUserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		User user = (User) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		User dbUser = userService.findByUserName(user.getCn_user_name());
		Set<String> shiroPermissions = new HashSet<>();
		Set<String> roleSet = new HashSet<String>();
		Set<Role> roles = dbUser.getRoles();
		for (Role role : roles) {
			Set<Resource> resources = role.getResources();
			for (Resource resource : resources) {
				shiroPermissions.add(resource.getSourceKey());
				
			}
			roleSet.add(role.getRoleKey());
		}
		authorizationInfo.setRoles(roleSet);
		authorizationInfo.setStringPermissions(shiroPermissions);
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();

		User user = userService.findByUserName(username);
		// 账号不存在
		if (user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}
		Object credentials = token.getCredentials();
		if (credentials == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}
		String password = new String((char[]) credentials);
		// 密码错误
		if (!MD5Utils.md5(password).equals(user.getCn_user_password())) {
			throw new IncorrectCredentialsException("账号或密码不正确");
		}
		// 账号锁定
		if (user.getCn_user_locked() == 1) {
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());

		return info;
	}

}
