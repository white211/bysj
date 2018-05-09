package cn.white.bysj.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * Create by @author white
 *
 * @date 2018-05-09 16:57
 */
@Data
public class NoteVo {

    /**
     * 笔记 id
     */
    private Integer cn_note_id=null;

    //笔记创造者id
    private Integer cn_user_id=null;

    //所属笔记本id
    private Integer cn_note_book_id=null;

    //笔记内容
    private String cn_note_content=null;

    //笔记标题
    private String cn_note_title=null;

    //笔记描述
    private String cn_note_desc=null;

    //笔记所属类型id
    private Integer cn_note_type_id;

    //笔记创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cnNoteCreateTime =null;

    //笔记最后更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cnNoteUpdateTime =null;

    //笔记所属标签id
    private Integer cn_note_label_id = null;

    //分享笔记阅读量
    private Integer cn_note_read=0;

    //笔记是否加密 1 不加密 0 加密
    private Integer cnNoteIsEncrypt= 1;

    //笔记是否分享状态中 0 是 1 否
    private Integer cnNoteIsShare=1;


}
