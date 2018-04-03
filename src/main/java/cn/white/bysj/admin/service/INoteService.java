package cn.white.bysj.admin.service;

import cn.white.bysj.admin.service.support.IBaseService;
import cn.white.bysj.admin.vo.NoteVo;
import cn.white.bysj.note.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Create by @author white
 *
 * @date 2018-03-05 12:09
 */
public interface INoteService  extends IBaseService<Note,Integer>{

     Page<NoteVo> findNote(Pageable pageable);

}
