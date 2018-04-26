package cn.white.bysj.admin.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * Create by @author white
 *
 * @date 2018-04-24 11:42
 */
@Data
public class HomeVo {

    private Integer cnHomeId;

    private String cnHomeType;

    @JSONField(format = "yyyy-MM-DD hh:ss:mm")
    private Date cnHomeCreateTime;

    private String cnHomeUserName;


}
