package cn.white.bysj.feedback;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Create by @author white
 *
 * @date 2018-04-01 21:49
 */
@Entity
@Data
public class Feedback {

    //用户反馈id
    @Id
    @GeneratedValue
    private Integer cn_feedback_id=null;

    //反馈内容
    private Integer cn_feedback_content=null;

    //反馈用户id
    private Integer cn_user_id=null;

    //反馈创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cn_feedback_creteTime=null;


}
