package cn.white.bysj.note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create by @author white
 *
 * @date 2018-03-02 21:36
 */
public interface NoteDao  extends JpaRepository<Note,Integer>{

    @Query(value = "select * from note where  cn_user_id = ?2 and cn_note_book_id = ?3 and (cn_note_title LIKE %?1%  or cn_note_content like %?1% ) ORDER BY cn_note_update_time DESC ",nativeQuery = true)
    List<Note> findNoteByTitleOrContent(String searchText,int userId,int noteBookId);

    @Query(value = "select * from note where cn_note_book_id = ?1 and cn_note_type_id != 4 ORDER BY cn_note_update_time DESC",nativeQuery = true)
    List<Note> findNoteByNoteBookId(Integer notebookid);

    @Query(value = "select count(*) from note where cn_note_book_id = ?1 and cn_note_type_id != 4",nativeQuery = true)
    int countByNoteBookId(Integer notebookid);

    @Query(value = "select * from note where cn_user_id =?1 and cn_note_type_id != 4 ORDER BY cn_note_update_time DESC ",nativeQuery = true)
    List<Note> findNoteByUserId(int userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE note SET cn_note_type_id =?1 where cn_note_id = ?2",nativeQuery = true)
     void updateNoteTypeId(int typeId,int noteId);

    @Query(value = "select count(*) from note where cn_note_label_id =?1 and cn_note_type_id !=4 ",nativeQuery = true)
    int countByLabelId(int labelId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE note SET cn_note_read =?2 where cn_note_id = ?1",nativeQuery = true)
    void updateNoteReadById(int noteId,int read);

    @Query(value = "select * from note where cn_user_id = ?1 and cn_note_type_id = ?2 ORDER BY cn_note_update_time DESC" ,nativeQuery = true)
    List<Note> findNoteByTypeId(int userId,int typeId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM  note WHERE  cn_note_id = ?1",nativeQuery = true)
    void deleteByNoteId(int userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE note SET  cn_note_type_id = ?1 WHERE cn_note_book_id =?2",nativeQuery = true)
    void updateNoteTypeIdByNoteBookId(int noteTypeId,int noteBookId);

    @Query(value = "select * from note where cn_note_label_id = ?1 and cn_note_type_id !=4 ORDER BY cn_note_update_time DESC",nativeQuery = true)
    List<Note> findNoteByLabelId(int labelId);

}

