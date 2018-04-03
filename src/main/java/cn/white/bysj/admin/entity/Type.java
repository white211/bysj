package cn.white.bysj.admin.entity;

import cn.white.bysj.admin.entity.support.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Create by @author white
 *
 * @date 2018-04-02 14:10
 */
@Data
@Entity
@Table(name = "tb_type")
public class Type extends BaseEntity{

   //类型id
   @Id
   @GeneratedValue
   private Integer cn_type_id;

   //类型描述
    private String cn_type_desc;

    //类型名称
    private String cn_type_name;

    //类型代码
    private String cn_type_code;

}
