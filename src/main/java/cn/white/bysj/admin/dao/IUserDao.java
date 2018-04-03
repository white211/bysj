package cn.white.bysj.admin.dao;

import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends IBaseDao<User, Integer> {

	@Query(value = "select * from user where cn_user_name=?1",nativeQuery = true)
	User findByCn_user_name(String username);

	@Query(value = "select * from user where cn_user_nickname =?1 ORDER BY ?#{#pageable}",nativeQuery = true)
	Page<User> findAllByCn_user_nicknameContaining(String searchText, Pageable pageable);

    @Query(value = "select cn_user_email from user where cn_user_id = ?1",nativeQuery = true)
	String findEmailById(int userId);


}
