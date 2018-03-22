package cn.white.bysj.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Create by @author white
 *
 * @date 2018-03-21 13:27
 */
public interface CommentDao extends JpaRepository<Comment,Integer>{

    @Query(value = "select * from comment where cn_note_id =?1 ORDER BY cn_comment_creat_time DESC",nativeQuery = true)
    List<Comment> findAllByNoteId(int noteId);

}
