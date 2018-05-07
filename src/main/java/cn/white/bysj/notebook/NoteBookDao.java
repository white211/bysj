package cn.white.bysj.notebook;


import cn.white.bysj.note.Note;
import cn.white.bysj.vo.NoteListVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create by @author white
 *
 *  2018-01-31 15:17
 */
@Mapper
public interface NoteBookDao extends JpaRepository<NoteBook,Integer> {

    @Query(value = "select count(*) from note_book where cn_notebook_name=?1 and cn_user_id =?2 and cn_notebook_type_id != 4",nativeQuery = true)
    int findNoteBookByName(String noteBookName,int userId);

    @Query(value = "select * from note_book where cn_user_id = ?1 and cn_notebook_type_id !=4 ORDER BY cn_notebook_update_time DESC ",nativeQuery = true)
    List<NoteBook> findNoteBookByUserId(int userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM note_book where cn_notebook_id =?1",nativeQuery = true)
    void  deleteByNoteBookId(int noteBookId);

    @Query(value = "select count(*) from note_book where cn_notebook_name=?1 and cn_user_id =?3 and cn_notebook_id != ?2 and cn_notebook_type_id != 4",nativeQuery = true)
    int findNoteBookByNameAndUserId(String noteBookName,int noteBookId,int userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update note_book set cn_notebook_name= ?3 where cn_notebook_id = ?2 and cn_user_id=?1",nativeQuery = true)
    void UpdateNoteBookName(int userId,int notebookid,String newName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update note_book set cn_notebook_type_id = ?3 where cn_notebook_id = ?2 and cn_user_id=?1",nativeQuery = true)
    void updateNoteBookType(int userId,int notebookid,int noteBookType);

    @Query(value = "select * from note_book where cn_notebook_name like %?1% and cn_user_id = ?2 AND cn_notebook_type_id !=?3",nativeQuery = true)
    List<NoteBook>  SearchNoteBook(String searchText,int userId,int noteBookTypeId);

   @Query(value = "SELECT * FROM note_book where cn_user_id = ?1 AND  cn_notebook_type_id = ?2",nativeQuery = true)
    List<NoteBook> findNoteBookByTypeId(int userId,int noteBookTypeId);

   @Transactional
   @Modifying(clearAutomatically = true)
   @Query(value = "delete  from note_book where  cn_user_id =?1 and  cn_notebook_type_id = 4",nativeQuery = true)
    void deleteAllByCn_user_id(int userId);

}
