package cn.white.bysj.admin.entity;

import cn.white.bysj.admin.entity.support.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.data.solr.core.mapping.SolrDocument;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Create by @author white
 *
 * @date 2018-04-01 22:03
 */
@Data
@Entity
@Table(name = "tb_shield")
public class Shield extends BaseEntity{

    //屏蔽词的id
    @GeneratedValue
    @Id
    private Integer cn_shield_id=null;

    //屏蔽词内容
    private String cn_shield_content=null;

    //屏蔽词创建时间
    @JSONField(format = "yyyy-MM-dd")
    private Date cnShieldCreateTime=null;

    //创建者id
    private Integer cn_user_id=null;


}
