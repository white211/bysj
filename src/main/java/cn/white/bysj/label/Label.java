package cn.white.bysj.label;

import cn.white.bysj.admin.entity.support.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Create by @author white
 *
 * @date 2018-03-02 21:39
 */
@Entity
@Data
public class Label extends BaseEntity {

    /*标签id*/
    @Id @GeneratedValue
    private Integer cn_label_id=null;

    /*标签名称*/
    private String cn_label_name=null;

    /*标签描述*/
    private String cn_label_desc=null;

    /*标签创建时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cnLabelCreateTime=null;

    /*最后更新时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cnLabelUpdateTime=null;

    /*标签创建者id*/
    private Integer cn_user_id=null;

}
