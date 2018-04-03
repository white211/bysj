package cn.white.bysj.admin.dao;

import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.notebook.NoteBook;
import org.springframework.data.jpa.repository.Query;

/**
 * Create by @author white
 *
 * @date 2018-04-02 11:57
 */
public interface INoteBookDao extends IBaseDao<NoteBook, Integer> {

    @Query(value = "select cn_notebook_name from note_book where cn_notebook_id=?1", nativeQuery = true)
    String findNoteBook(int notebookId);


}
