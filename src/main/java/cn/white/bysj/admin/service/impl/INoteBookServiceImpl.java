package cn.white.bysj.admin.service.impl;

import cn.white.bysj.admin.dao.INoteBookDao;
import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.admin.service.INoteBookService;
import cn.white.bysj.admin.service.support.impl.BaseServiceImpl;
import cn.white.bysj.notebook.NoteBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by @author white
 *
 * @date 2018-04-02 12:45
 */
@Service
public class INoteBookServiceImpl extends BaseServiceImpl<NoteBook, Integer> implements INoteBookService {

    @Autowired
    private INoteBookDao iNoteBookDao;

    @Override
    public IBaseDao<NoteBook, Integer> getBaseDao() {
        return this.iNoteBookDao;
    }



}
