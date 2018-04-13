package cn.white.bysj.admin.entity;

import cn.white.bysj.admin.entity.support.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Create by @author white
 *
 * @date 2018-04-05 20:09
 */
@Entity
@Data
@Table(name = "tb_home")
public class Home extends BaseEntity {

    //首页信息id
    @Id
    @GeneratedValue
    private Integer cn_home_id;

    //第一张大图片url
    private String cn_first_bpic;

    //第一张小图
    private String cn_first_spic;

   //第一张描述标题
    private String cn_first_title;

   //第二张描述
    private String cn_first_desc;

    //第二张大图片url
    private String cn_second_bpic;

    //第二张小图片
    private String cn_second_spic;

    //第二张描述标题
    private String cn_second_title;

    //第二张描述
    private String cn_second_desc;


    //第三张大图片url
    private String cn_third_bpic;

    //第三张小图片
    private String cn_third_spic;

    //第三张描述标题
    private String cn_third_title;

    //第三张描述
    private String cn_third_desc;

    //创建者id
    private Integer cn_user_id;

    //创建时间
    @JSONField(format = "yyyy-MM-DD hh:ss:mm")
    private Date cnHomeCreateTime;

    //使用状态
    private String cn_home_type;

    //更新时间
    @JSONField(format = "yyyy-MM-DD hh:ss:mm")
    private Date cnHomeUpdateTime;
}
