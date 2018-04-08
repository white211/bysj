package cn.white.bysj.admin.service.impl;

import cn.white.bysj.admin.dao.*;
import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.admin.service.INoteService;
import cn.white.bysj.admin.service.support.impl.BaseServiceImpl;
import cn.white.bysj.admin.vo.NoteVo;
import cn.white.bysj.note.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
        Page<Note> page = iNoteDao.findAll(pageable);
        List<NoteVo> noteVoList = new ArrayList<>();
        for (Note note:page){
            NoteVo noteVo = new NoteVo();
            noteVo.setCn_note_id(note.getCn_note_id());
            noteVo.setCn_note_title(note.getCn_note_title());
            noteVo.setCn_note_content(note.getCn_note_content());
            noteVo.setCn_note_read(note.getCn_note_read());
            noteVo.setCn_note_creatTime(note.getCnNoteCreateTime());
            noteVo.setCn_note_desc(note.getCn_note_desc());
            noteVo.setCn_notebook_lastupdateTime(note.getCnNoteUpdateTime());
            String email = iUserDao.findEmailById(note.getCn_user_id());
            noteVo.setCn_user_email(email);
            if (!(note.getCn_note_book_id()==null)){
                String notebook = iNoteBookDao.findNoteBook(note.getCn_note_book_id());
                noteVo.setCn_notebook_name(notebook);
            }
            if (!(note.getCn_note_type_id()==null)){
                String typeName = iTypeDao.findNoteType(note.getCn_note_type_id());
                noteVo.setCn_noteType_name(typeName);
            }
            if (!(note.getCn_note_label_id()==null)){
                String labelName= iLabelDao.findLabel(note.getCn_note_label_id());
                noteVo.setCn_notelabel_name(labelName);
            }
            noteVoList.add(noteVo);
        }
        Page<NoteVo> noteVos = new PageImpl<>(noteVoList,pageable,page.getTotalElements());
        return noteVos;
    }

}
