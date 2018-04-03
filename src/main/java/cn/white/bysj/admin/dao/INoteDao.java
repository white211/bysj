package cn.white.bysj.admin.dao;

import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.note.Note;
import cn.white.bysj.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Create by @author white
 *
 * @date 2018-03-05 12:08
 */
@Repository
public interface INoteDao extends IBaseDao<Note,Integer> {


}
