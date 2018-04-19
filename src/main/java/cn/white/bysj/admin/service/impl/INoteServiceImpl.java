package cn.white.bysj.admin.service.impl;

import cn.white.bysj.admin.dao.*;
import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.admin.service.INoteService;
import cn.white.bysj.admin.service.support.impl.BaseServiceImpl;
import cn.white.bysj.admin.vo.NoteVo;
import cn.white.bysj.commons.Constant;
import cn.white.bysj.note.Note;
import groovy.util.logging.Slf4j;
import org.apache.ibatis.io.ResolverUtil;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Create by @author white
 *
 * @date 2018-03-05 12:10
 */
@Service
@Slf4j
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

    @Autowired
    private TransportClient client;

    private static Logger logger = LoggerFactory.getLogger(INoteServiceImpl.class);

    @Override
    public IBaseDao<Note, Integer> getBaseDao() {
        return this.iNoteDao;
    }

    @Override
    public Page<NoteVo> findNote(Pageable pageable) {
//        Pageable pageable1 = new PageRequest(pageable.getPageNumber(),pageable.getPageSize(),Sort.Direction.DESC,"cnNoteCreateTime");
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

    @Override
    public Page<NoteVo> findByText(String text,Pageable pageable) {
        Page<Note> page = iNoteDao.findNoteByText(text,pageable);
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

    /**
     * TODO: 从es上分页查询笔记
     * @author white
     * @date 2018-04-18 0:05

     * @return
     * @throws
     */
    @Override
    public Page<NoteVo> findByTextInEs(String text, Pageable pageable) {
        String string = "cnNoteCreateTime";
        Sort sort = new Sort(Sort.Direction.DESC,string);
        Pageable pageable1 = new PageRequest(pageable.getPageNumber(),pageable.getPageSize(),sort);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<NoteVo> noteVoList = new ArrayList<>();
        String [] arr = {"cnNoteTitle","cnNoteContent","cnNoteType",
                "cnUserEmail","cnNoteBookName","cnNoteRead","cnNoteLabelName"};
        try{
            SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(Constant.ES_INDEX_NAME).setTypes(Constant.ES_TYPE_NAME);
            SearchResponse response=searchRequestBuilder.setQuery(QueryBuilders.multiMatchQuery(text,arr))
                    .addSort("cnNoteCreateTime",SortOrder.DESC)
                    .setFrom(pageable.getPageNumber()*10)
                    .setSize(pageable.getPageSize())
                    .setExplain(true)
                    .execute()
                    .actionGet();
            SearchHits hits = response.getHits();
            for (int i = 0; i < hits.getHits().length; i++) {
                NoteVo noteVo = new NoteVo();
                noteVo.setCn_note_id(Integer.parseInt(hits.getHits()[i].getId()));
                noteVo.setCn_note_content(hits.getHits()[i].getSource().get("cnNoteContent").toString());
                noteVo.setCn_user_email(hits.getHits()[i].getSource().get("cnUserEmail").toString());
                noteVo.setCn_note_title(hits.getHits()[i].getSource().get("cnNoteTitle").toString());
                noteVo.setCn_notelabel_name(hits.getHits()[i].getSource().get("cnNoteLabelName").toString());
                noteVo.setCn_noteType_name(hits.getHits()[i].getSource().get("cnNoteType").toString());
                noteVo.setCn_notebook_name(hits.getHits()[i].getSource().get("cnNoteBookName").toString());
                noteVo.setCn_note_read(Integer.parseInt(hits.getHits()[i].getSource().get("cnNoteRead").toString()));
                noteVo.setCn_note_creatTime(sdf.parse(hits.getHits()[i].getSource().get("cnNoteCreateTime").toString()));
                noteVoList.add(noteVo);
            }
            Page<NoteVo> noteVos = new PageImpl<>(noteVoList,pageable,hits.getTotalHits());
            return noteVos;
        }catch (Exception e){
            logger.error("es查询笔记失败");
            return this.findByText(text,pageable1);
        }
    }

    /**
     * TODO: 从es上面获取笔记记录
     * @author white
     * @date 2018-04-18 16:05

     * @return
     * @throws
     */
    @Override
    public Page<NoteVo> findNoteInEs(Pageable pageable) {
        String string = "cnNoteCreateTime";
        Sort sort = new Sort(Sort.Direction.DESC,string);
        Pageable pageable1 = new PageRequest(pageable.getPageNumber(),pageable.getPageSize(),sort);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<NoteVo> noteVoList = new ArrayList<>();
        try{
            SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(Constant.ES_INDEX_NAME).setTypes(Constant.ES_TYPE_NAME);
            SearchResponse response=searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery())
                    .addSort("cnNoteCreateTime",SortOrder.DESC)
                    .setFrom(pageable.getPageNumber()*10)
                    .setSize(pageable.getPageSize())
                    .setExplain(true)
                    .execute()
                    .actionGet();
            SearchHits hits = response.getHits();
            for (int i = 0; i < hits.getHits().length; i++) {
                NoteVo noteVo = new NoteVo();
                noteVo.setCn_note_id(Integer.parseInt(hits.getHits()[i].getId()));
                noteVo.setCn_note_content(hits.getHits()[i].getSource().get("cnNoteContent").toString());
                noteVo.setCn_user_email(hits.getHits()[i].getSource().get("cnUserEmail").toString());
                noteVo.setCn_note_title(hits.getHits()[i].getSource().get("cnNoteTitle").toString());
                noteVo.setCn_notelabel_name(hits.getHits()[i].getSource().get("cnNoteLabelName").toString());
                noteVo.setCn_noteType_name(hits.getHits()[i].getSource().get("cnNoteType").toString());
                noteVo.setCn_notebook_name(hits.getHits()[i].getSource().get("cnNoteBookName").toString());
                noteVo.setCn_note_read(Integer.parseInt(hits.getHits()[i].getSource().get("cnNoteRead").toString()));
                noteVo.setCn_note_creatTime(sdf.parse(hits.getHits()[i].getSource().get("cnNoteCreateTime").toString()));
                noteVoList.add(noteVo);
            }
            Page<NoteVo> noteVos = new PageImpl<>(noteVoList,pageable,hits.getTotalHits());
            return noteVos;
        }catch (Exception e){
            logger.error("es获取笔记失败");
            return this.findNote(pageable1);
        }
    }

}
