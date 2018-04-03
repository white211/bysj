package cn.white.bysj.admin.service.impl;

import cn.white.bysj.admin.dao.*;
import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.admin.service.INoteService;
import cn.white.bysj.admin.service.support.impl.BaseServiceImpl;
import cn.white.bysj.admin.vo.NoteVo;
import cn.white.bysj.note.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Create by @author white
 *
 * @date 2018-03-05 12:10
 */
@Service
public class INoteServiceImpl extends BaseServiceImpl<Note, Integer>  implements INoteService {

    @Autowired
    private INoteDao iNoteDao;

    @Autowired
    private IUserDao iUserDao;

    @Autowired
    private INoteBookDao iNoteBookDao;

    @Autowired
    private ITypeDao iTypeDao;

    @Autowired
    private ILabelDao iLabelDao;

    @Override
    public IBaseDao<Note, Integer> getBaseDao() {
        return this.iNoteDao;
    }


    @Override
    public Page<NoteVo> findNote(Pageable pageable) {
        List<NoteVo> noteVoList = new ArrayList<>();
        Page<Note> page = iNoteDao.findAll(pageable);
        for (Note note:page){
            NoteVo noteVo = new NoteVo();
            noteVo.setCn_note_id(note.getCn_note_id());
            noteVo.setCn_note_title(note.getCn_note_title());
            noteVo.setCn_note_content(note.getCn_note_content());
            noteVo.setCn_note_read(note.getCn_note_read());
            noteVo.setCn_note_creatTime(note.getCn_note_createTime());
            noteVo.setCn_note_desc(note.getCn_note_desc());
            noteVo.setCn_notebook_lastupdateTime(note.getCn_note_updateTime());
            String email = iUserDao.findEmailById(note.getCn_user_id());
            noteVo.setCn_user_email(email);
            String notebook = iNoteBookDao.findNoteBook(note.getCn_note_book_id());
            noteVo.setCn_notebook_name(notebook);
            String typeName = iTypeDao.findNoteType(note.getCn_note_type_id());
            noteVo.setCn_noteType_name(typeName);
            String labelName= iLabelDao.findLabel(note.getCn_note_label_id());
            noteVo.setCn_notelabel_name(labelName);
            noteVoList.add(noteVo);
        }
        Page<NoteVo> noteVo = new PageImpl<NoteVo>(noteVoList);
        return noteVo;
    }


}
