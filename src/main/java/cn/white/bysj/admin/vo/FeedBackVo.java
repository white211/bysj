package cn.white.bysj.admin.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.sun.tools.corba.se.idl.StringGen;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Create by @author white
 *
 * @date 2018-04-05 11:35
 */
@Data
public class FeedBackVo {
    //用户反馈id
    @Id
    @GeneratedValue
    private Integer cn_feedback_id=null;

    //反馈内容
    private String cn_feedback_content=null;

    //反馈用户id
    private String cn_user_email=null;

    //反馈创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date cn_feedback_createTime=null;

    //反馈图片url
    private String imageurl = null;

    //回复内容
    private String cn_feedback_returnContent=null;

    //反馈类型
    private String cn_feedback_type=null;

    //是否回复
    private Integer cn_feedback_isReturn=null;

    //回复人id
    private String cn_userReturn_name=null;


}
