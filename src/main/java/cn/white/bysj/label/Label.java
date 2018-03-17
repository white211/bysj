package cn.white.bysj.label;

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
public class Label {

    @Id @GeneratedValue
    private Integer cn_label_id=null;//标签id
    private String cn_label_name=null;//标签名称
    private String cn_label_desc=null;//标签描述
    @JSONField(format = "yyyy-MM-dd")
    private Date cn_label_createTime=null;//标签创建时间
    @JSONField(format = "yyyy-MM-dd")
    private Date cn_label_last_updateTime=null;//最后更新时间
    private Integer cn_user_id=null;//标签创建者id

}
