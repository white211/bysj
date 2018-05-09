package cn.white.bysj.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Create by @author white
 *
 * @date 2018-05-09 16:30
 */
@Data
public class UserVo {

    /*//用户id*/
    private Integer cn_user_id = null;

    /*//用户名*/
    private String cn_user_name = null;

    /*//用户邮箱*/
    private String cn_user_email = null;

    /*//用户密码*/
    private String cn_user_password = null;

    /*//用户手机号*/
    private String cn_user_telephone = null;

    /*//用户令牌*/
    private String cn_user_token = null;

    /*//用户昵称*/
    private String cn_user_nickname = null;

    /*//用户激活状态*/
    private String cn_user_actived = null;

    /*//用户激活码*/
    private String cn_user_actived_code = null;

    /*//用户性别 0 女 1 男*/
    private Integer cn_user_sex;

    /*//头像地址*/
    private String cn_user_avatar;

    /*//出生日期*/
    @JSONField(format = "yyyy-MM-dd")
    private Date cn_user_birthday;

    /*//用户住址*/
    private String cn_user_address;

    /*逻辑删除状态0 未删除 1 删除*/
    private Integer cn_user_deleteStatus;

    /*//用户是否锁定 0未锁定 1 锁定*/
    private Integer cn_user_locked;

    /*//用户描述*/
    private String cn_user_description;

    /*//用户创建时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cnUserCreateTime;

    /*//用户更新时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cnUserUpdateTime;

    //笔记的阅读密码
    private String cnNoteReadPassword;

}
