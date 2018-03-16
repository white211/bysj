package cn.white.bysj.vo;

import cn.white.bysj.note.Note;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Create by @author white
 *
 * @date 2018-03-12 10:44
 */
@Data
public class NoteListVo {

    private Integer cn_notebook_id;//笔记本id
    private Integer cn_notebook_type_id;//笔记本类型id
    private Integer cn_user_id;//用户id
    private String cn_notebook_name;//笔记本名称
    private String  cn_notebook_desc;//笔记本说明
    @JSONField(format = "yyyy-MM-dd")
    private Date cn_notebook_createTime;//笔记本创建时间
    @JSONField(format = "yyyy-MM-dd")
    private Date cn_notebook_lastupdateTime;//笔记本最后更新时间
    private Integer cn_notebook_label_id;//笔记本所属标签

    private List<Note> noteList;//笔记数据
    private Integer noteCount;//笔记条数

}
