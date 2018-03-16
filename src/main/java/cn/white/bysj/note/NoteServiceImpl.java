package cn.white.bysj.note;

import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.utils.ValidatorUtil;
import groovy.util.logging.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-03-02 21:37
 */
@Service
@Slf4j
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteDao noteDao;

    @Override
    /*
    新建笔记记录
    参数包括
     */
    public ServerResponse<Note> newNote(Map<String, Object> map) {
        List<String> list = Arrays.asList("noteBookId","noteContent","noteTitle","noteDesc","noteLabel");
        if (ValidatorUtil.validator(map,list).size()>0){
           return ServerResponse.createByErrorMessage("缺少参数，应包括noteBookId,noteContent,noteTitle,noteDesc,noteLabel");
        }
         if (StringUtils.isBlank(map.get("noteTitle").toString())){
              return ServerResponse.createByErrorMessage("笔记标题不能为空");
         }else if (StringUtils.isBlank(map.get("noteContent").toString())){
             return ServerResponse.createByErrorMessage("笔记内容不能为空");
         }else {
             Note note = new Note();
             note.setCn_note_title(map.get("noteTitle").toString());
             note.setCn_note_content(map.get("noteContent").toString());
             note.setCn_note_book_id(Integer.parseInt(map.get("noteBookId").toString()));
             note.setCn_note_desc(map.get("noteDesc").toString());
             note.setCn_note_label_id(Integer.parseInt(map.get("noteLabel").toString()));
             note.setCn_note_creatTime(new Date());
             note.setCn_notebook_lastupdateTime(new Date());
             noteDao.save(note);
             return ServerResponse.createBySuccess("新建笔记成功",note);
         }
    }

    /*
    删除笔记记录
    参数包括
     */
    @Override
    public ServerResponse deleteNote(Map<String, Object> map) {

        List<String> list = Arrays.asList("noteId");
        if (ValidatorUtil.validator(map,list).size()>0){
            return ServerResponse.createByErrorMessage("缺少参数，应该包括noteID");
        }
         if (StringUtils.isBlank(map.get("noteID").toString())){
            return ServerResponse.createByErrorMessage("删除笔记id不能为空");
         }else{
             noteDao.delete(Integer.parseInt(map.get("noteID").toString()));
             return ServerResponse.createBySuccessMessags("删除成功");
         }
    }

    /*
    通过标题跟内容查找笔记记录
    参数包括
     */
    @Override
    public ServerResponse<List<Note>> findNoteByTitleOrContent(Map<String, Object> map) {
        List<String> list = Arrays.asList("searchText");
        if (ValidatorUtil.validator(map,list).size()>0){
            return ServerResponse.createByErrorMessage("缺少参数，应该包括searchText");
        }

        if(StringUtils.isBlank(map.get("SearchText").toString())){
            return ServerResponse.createByErrorMessage("搜索内容不能为空");
        }else{
             List<Note> noteList = noteDao.findNoteByTitleOrContent(map.get("SearchText").toString());
           return ServerResponse.createBySuccess("搜索结果",noteList);
        }
    }


}
