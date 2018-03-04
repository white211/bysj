package cn.white.bysj.note;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Create by @author white
 *
 * @date 2018-03-02 21:23
 */
@Entity
@Data
public class Note {
     @Id @GeneratedValue
    private Integer cn_note_id;//笔记id
    private Integer cn_note_book_id;//所属笔记本id
    private String cn_note_content;//笔记内容
    private String cn_note_title;//笔记标题
    private String cn_note_desc;//笔记描述
    private String cn_note_creatTime;//笔记创建时间
    private String cn_notebook_lastupdateTime;//笔记本最后更新时间
    private String cn_note_label;//笔记所属标签

}
