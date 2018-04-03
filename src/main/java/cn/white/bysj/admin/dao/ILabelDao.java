package cn.white.bysj.admin.dao;

import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.label.Label;
import org.springframework.data.jpa.repository.Query;

/**
 * Create by @author white
 *
 * @date 2018-04-02 12:57
 */
public interface ILabelDao extends IBaseDao<Label,Integer>{
    @Query(value = "select cn_label_name from label where cn_label_id = ?1",nativeQuery = true)
    String findLabel(int cn_label_id);
}
