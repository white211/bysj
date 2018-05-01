package cn.white.bysj.admin.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * Create by @author white
 *
 * @date 2018-04-01 23:58
 */
@Data
public class NoteVo {

    private Integer cn_note_id=null;

    //笔记创造者
    private String cn_user_email=null;

    //所属笔记本
    private String cn_notebook_name=null;

    //笔记内容
    private String cn_note_content=null;

    //笔记标题
    private String cn_note_title=null;

    //笔记描述
    private String cn_note_desc=null;

    //笔记所属类型
    private String cn_noteType_name;

    //笔记创建时间
    @JSONField(format = "yyyy-MM-dd")
    private Date cn_note_creatTime =null;

    //笔记本最后更新时间
    @JSONField(format = "yyyy-MM-dd")
    private Date cn_notebook_lastupdateTime =null;

    //笔记所属标签
    private String cn_notelabel_name = null;

    //分享笔记阅读量
    private Integer cn_note_read=0;

    /**
     *笔记时候加密
     */
    private Integer cnNoteIsEncrypt;

}
