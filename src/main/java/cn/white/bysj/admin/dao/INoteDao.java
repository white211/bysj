package cn.white.bysj.admin.dao;

import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.note.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Create by @author white
 *
 * @date 2018-03-05 12:08
 */
@Repository
public interface INoteDao extends IBaseDao<Note, Integer> {

    @Query(value = "select * from note where cn_note_title LIKE %?1%  or cn_note_content LIKE %?1%" +
            " ORDER BY ?#{#pageable}", nativeQuery = true)
    Page<Note> findNoteByText(String text, Pageable pageable);

}
