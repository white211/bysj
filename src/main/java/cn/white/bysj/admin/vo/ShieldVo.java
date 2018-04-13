package cn.white.bysj.admin.vo;

import lombok.Data;

import java.util.Date;

/**
 * Create by @author white
 *
 * @date 2018-04-02 15:01
 */
@Data
public class ShieldVo {

    /*屏蔽词id*/
    private Integer cn_shield_id;

    /*屏蔽词内容*/
    private String cn_shield_content;

    /*创建时间*/
    private Date cn_shield_createTime;

    /*创建者id*/
    private Integer cn_user_id;

    /*创建者*/
    private String cn_user_name;

}
