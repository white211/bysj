package cn.white.bysj.admin.dao;

import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.admin.entity.Type;
import org.springframework.data.jpa.repository.Query;

/**
 * Create by @author white
 *
 * @date 2018-04-02 12:54
 */
public interface ITypeDao extends IBaseDao<Type,Integer> {

    @Query(value = "select cn_type_name from tb_type where cn_type_id=?1",nativeQuery = true)
    String findNoteType(int cn_note_type_id);
}
