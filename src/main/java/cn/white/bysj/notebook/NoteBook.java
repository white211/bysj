package cn.white.bysj.notebook;

import cn.white.bysj.admin.entity.support.BaseEntity;
import cn.white.bysj.note.Note;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * Create by @author white
 *
 * @date 2018-01-31 15:15
 */
@Entity
@Data
public class NoteBook extends BaseEntity {
    @Id @GeneratedValue
    private Integer cn_notebook_id =null;//笔记本id
    private Integer cn_notebook_type_id=null;//笔记本类型id
    private Integer cn_user_id=null;//用户id
    private String cn_notebook_name=null;//笔记本名称
    private String  cn_notebook_desc=null;//笔记本说明
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cn_notebook_createTime=null;//笔记本创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cn_notebook_updateTime=null;//笔记本最后更新时间
    private Integer cn_notebook_label_id=null;//笔记本所属标签id

}

