package cn.white.bysj.label;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Create by @author white
 *
 * @date 2018-03-02 21:39
 */
@Entity
@Data
public class Label {

    @Id @GeneratedValue
    private Integer cn_label_id;//标签id
    private String cn_label_name;//标签名称
    private String cn_label_desc;//标签描述
    private String cn_label_createTime;//标签创建时间

}
