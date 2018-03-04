package cn.white.bysj.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Create by @author white
 *
 * @date 2017-12-28 15:42
 */
@Entity
@Data
public class User {
  @Id @GeneratedValue
    private Integer cn_user_id;//用户id
    private String cn_user_name;//用户名
    private String cn_user_email;//用户邮箱
    private String cn_user_password;//用户密码
    private String cn_user_telephone;//用户手机号
    private String cn_user_token;//用户令牌
    private String cn_user_nickname;//用户昵称
    private String cn_user_actived;//用户激活状态
    private String cn_user_actived_code;//用户激活码

}
