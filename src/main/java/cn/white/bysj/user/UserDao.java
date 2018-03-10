package cn.white.bysj.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import cn.white.bysj.user.User;
import java.util.List;

/**
 * Create by @author white
 *
 * @date 2017-12-28 16:02
 */

public interface UserDao extends JpaRepository<User,Integer>{
    //根据激活码查询用户
    @Query(value = "select * from USER where cn_user_actived_code=?1",nativeQuery = true)
    User selectUserByCode(String code);

    //根据code更新状态跟code
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update user SET cn_user_actived=1,cn_user_actived_code=NULL WHERE cn_user_actived_code=?1",nativeQuery = true)
    void updateUserState(String code);

    //根据邮箱查找用户
    @Query(value = "select count(0) from user where cn_user_email=?1 AND cn_user_actived=1",nativeQuery = true)
    int selectUserByEmail(String email);

    //根据用户账号查找用户
    @Query(value = "select * from user where cn_user_email=?1 and cn_user_actived=1",nativeQuery = true)
    User selectUserByAccount(String account);

    //通过邮箱更新密码
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update user set cn_user_password =?1 where cn_user_email = ?2",nativeQuery = true)
    void forgetResetPassword(String password,String email);

    @Query(value = "select cn_user_password from user where email=?1",nativeQuery = true)
    String findPasswordByCn_user_email(String email);

}


