package cn.white.bysj.admin.service.impl;


import cn.white.bysj.admin.common.utils.MD5Utils;
import cn.white.bysj.admin.dao.IUserDao;
import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.admin.entity.Role;

import cn.white.bysj.admin.service.IRoleService;
import cn.white.bysj.admin.service.IUserService;
import cn.white.bysj.admin.service.support.impl.BaseServiceImpl;
import cn.white.bysj.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 用户账户表  服务实现类
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
@Service
public class IUserServiceImpl extends BaseServiceImpl<User, Integer> implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IRoleService roleService;
	
	@Override
	public IBaseDao<User, Integer> getBaseDao() {
		return this.userDao;
	}

	@Override
	public User findByUserName(String username) {
		return userDao.findByCn_user_name(username);
	}

	@Override
	@Transactional
	public void saveOrUpdate(User user) {
		if(user.getCn_user_id() != null){
			User dbUser = find(user.getCn_user_id());
			dbUser.setCn_user_nickname(user.getCn_user_nickname());
			dbUser.setCn_user_sex(user.getCn_user_sex());
			dbUser.setCn_user_birthday(user.getCn_user_birthday());
			dbUser.setCn_user_telephone(user.getCn_user_telephone());
			dbUser.setCn_user_email(user.getCn_user_email());
			dbUser.setCn_user_address(user.getCn_user_address());
			dbUser.setCn_user_locked(user.getCn_user_locked());
			dbUser.setCn_user_description(user.getCn_user_description());
			dbUser.setCn_user_updateTime(new Date());
			dbUser.setCn_user_avatar(user.getCn_user_avatar());
			update(dbUser);
		}else{
			user.setCn_user_createTime(new Date());
			user.setCn_user_updateTime(new Date());
			user.setCn_user_deleteStatus(0);
			user.setCn_user_password(MD5Utils.md5("111111"));
			save(user);
		}
	}
	
	@Override
	public void delete(Integer id) {
		User user = find(id);
		Assert.state(!"admin".equals(user.getCn_user_name()),"超级管理员用户不能删除");
		super.delete(id);
	}

	@Override
	public void grant(Integer id, String[] roleIds) {
		User user = find(id);
		Assert.notNull(user, "用户不存在");
		Assert.state(!"admin".equals(user.getCn_user_name()),"超级管理员用户不能修改管理角色");
		Role role;
		Set<Role> roles = new HashSet<Role>();
		if(roleIds != null){
			for (int i = 0; i < roleIds.length; i++) {
				Integer rid = Integer.parseInt(roleIds[i]);
				role = roleService.find(rid);
				roles.add(role);
			}
		}
		user.setRoles(roles);
		update(user);
	}

	@Override
	public Page<User> findAllByLike(String searchText, PageRequest pageRequest) {
		if(StringUtils.isBlank(searchText)){
			searchText = "";
		}
		return userDao.findAllByCn_user_nicknameContaining(searchText,pageRequest);
	}

	
	@Override
	public void updatePwd(User user, String oldPassword, String password1, String password2) {
		Assert.notNull(user, "用户不能为空");
		Assert.notNull(oldPassword, "原始密码不能为空");
		Assert.notNull(password1, "新密码不能为空");
		Assert.notNull(password2, "重复密码不能为空");
		
		User dbUser = userDao.findByCn_user_name(user.getCn_user_name());
		Assert.notNull(dbUser, "用户不存在");
		
		Assert.isTrue(user.getCn_user_password().equals(MD5Utils.md5(oldPassword)), "原始密码不正确");;
		Assert.isTrue(password1.equals(password2), "两次密码不一致");
		dbUser.setCn_user_password(MD5Utils.md5(password1));
		userDao.saveAndFlush(dbUser);
	}
	
}
