package cn.white.bysj.feedback;

import cn.white.bysj.admin.entity.support.BaseEntity;
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
public class Feedback extends BaseEntity {

    //用户反馈id
    @Id
    @GeneratedValue
    private Integer cn_feedback_id=null;

    //反馈内容
    private String cn_feedback_content=null;

    //反馈用户id
    private Integer cn_user_id=null;

    //反馈创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cnFeedbackCreateTime=null;

    //图片链接
    private String imageURL = null;

    //回复内容
    private String cn_feedback_returnContent=null;

    //反馈类型
    private String cn_feedback_type=null;

    //是否回复
    private Integer cn_feedback_isReturn=null;

    //回复人id
    private Integer cn_userReturn_id=null;


}
