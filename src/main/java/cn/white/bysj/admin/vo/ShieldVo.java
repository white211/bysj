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

    private Integer cn_shield_id;
    private String cn_shield_content;
    private Date cn_shield_createTime;
    private String cn_user_name;

}
