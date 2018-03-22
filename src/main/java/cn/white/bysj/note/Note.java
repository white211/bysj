package cn.white.bysj.note;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Create by @author white
 *
 * @date 2018-03-02 21:23
 */
@Entity
@Data
public class Note {
     @Id @GeneratedValue
    private Integer cn_note_id=null;//笔记id
    private Integer cn_user_id=null;//笔记创造者id
    private Integer cn_note_book_id=null;//所属笔记本id
    private String cn_note_content=null;//笔记内容
    private String cn_note_title=null;//笔记标题
    private String cn_note_desc=null;//笔记描述
    private Integer cn_note_type_id;//笔记所属类型id
    @JSONField(format = "yyyy-MM-dd")
    private Date cn_note_creatTime =null;//笔记创建时间
    @JSONField(format = "yyyy-MM-dd")
    private Date cn_notebook_lastupdateTime =null;//笔记本最后更新时间
    private Integer cn_note_label_id = null;//笔记所属标签id
    private Integer cn_note_read=0;//分享笔记阅读量


}
