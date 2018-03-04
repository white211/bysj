package cn.white.bysj.notebook;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Create by @author white
 *
 * @date 2018-01-31 15:15
 */
@Entity
@Data
public class NoteBook {
    @Id @GeneratedValue
    private Integer cn_notebook_id;//笔记本id
    private Integer cn_notebook_type_id;//笔记本类型id
    private Integer cn_user_id;//用户id
    private String cn_notebook_name;//笔记本名称
    private String  cn_notebook_desc;//笔记本说明
    private String cn_notebook_createTime;//笔记本创建时间
    private String cn_notebook_lastupdateTime;//笔记本最后更新时间
    private String cn_notebook_label;//笔记本所属标签
}

