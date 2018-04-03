package cn.white.bysj.vo;

import cn.white.bysj.note.Note;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Create by @author white
 *
 * @date 2018-03-18 23:02
 */
@Data
public class LabelListVo {

    private Integer cn_label_id=null;//标签id
    private String cn_label_name=null;//标签名称
    private String cn_label_desc=null;//标签描述
    @JSONField(format = "yyyy-MM-dd")
    private Date cn_label_createTime=null;//标签创建时间
    @JSONField(format = "yyyy-MM-dd")
    private Date cn_label_last_updateTime=null;//最后更新时间
    private Integer cn_user_id=null;//标签创建者id

    private List<Note> noteList;//笔记数据
    private Integer noteCount;//某一标签下的笔记条数（type=1）
    private String label_first_font;//标签的首字母


}
