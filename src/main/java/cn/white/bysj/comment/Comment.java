package cn.white.bysj.comment;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Create by @author white
 *
 * @date 2018-03-21 0:47
 */
@Entity
@Data
public class Comment {
    @Id @GeneratedValue
    private Integer cn_comment_id = null;//评论id
    private Integer cn_note_id=null;//评论所属笔记本id
    private String  cn_comment_content=null;//评论内容
    private Integer  cn_user_id=null;//评论人id
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cn_comment_creatTime=null;//评论时间
    private String cn_user_email=null;//评论人邮箱
    private String cn_user_avatar=null;//评论人头像
    private String cn_user_nickName=null;//评论人昵称

}
