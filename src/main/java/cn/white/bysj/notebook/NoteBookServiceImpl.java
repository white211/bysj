package cn.white.bysj.notebook;

import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.note.Note;
import cn.white.bysj.note.NoteDao;
import cn.white.bysj.utils.ValidatorUtil;
import cn.white.bysj.vo.NoteListVo;
import com.sun.tools.corba.se.idl.StringGen;
import groovy.util.logging.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.transport.ObjectTable;

import java.util.*;

/**
 * Create by @author white
 *
 * @date 2018-01-31 15:16
 */
@Service
@Slf4j
public class NoteBookServiceImpl implements NoteBookService {

    @Autowired
    private NoteBookDao noteBookDao;

    @Autowired
    private NoteDao noteDao;

    /*
     新建笔记本
     参数包括
     */
    @Override
    public ServerResponse newNoteBook(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId", "noteBookName");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，必须包括userId,noteBookName");
        }
        if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("用户id不能为空");
        } else if (StringUtils.isBlank(map.get("noteBookName").toString())) {
            return ServerResponse.createByErrorMessage("笔记本名称不能为空");
        } else {
            int count = noteBookDao.findNoteBookByName(map.get("noteBookName").toString());
            if (count > 0) {
                return ServerResponse.createByErrorMessage("该笔记本已经存在，请重新起个名字");
            } else {
                NoteBook noteBook = new NoteBook();
                noteBook.setCn_user_id(Integer.parseInt(map.get("userId").toString()));
                noteBook.setCn_notebook_name(map.get("noteBookName").toString());
                noteBook.setCn_notebook_createTime(new Date());
                noteBook.setCn_notebook_lastupdateTime(new Date());
                noteBook.setCn_notebook_type_id(1);
                noteBookDao.save(noteBook);
                return ServerResponse.createBySuccessMessags("创建成功");
            }
        }
    }

    /*
    查找全部笔记记录
    参数包括
     */
    @Override
    public ServerResponse<List<NoteListVo>> findAll(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，应包括userId");
        }
        if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("用户id不能为空");
        } else {
            List<NoteListVo> noteList = new ArrayList<>();

            List<NoteBook> noteBookList = noteBookDao.findNoteBookByUserId(Integer.parseInt(map.get("userId").toString()));
            for (NoteBook notebooklist : noteBookList) {
                NoteListVo noteListVo = new NoteListVo();
                noteListVo.setCn_notebook_id(notebooklist.getCn_notebook_id());
                noteListVo.setCn_notebook_name(notebooklist.getCn_notebook_name());
                noteListVo.setCn_notebook_createTime(notebooklist.getCn_notebook_createTime());
                noteListVo.setCn_notebook_lastupdateTime(notebooklist.getCn_notebook_lastupdateTime());
                noteListVo.setCn_notebook_label_id(notebooklist.getCn_notebook_label_id());
                noteListVo.setCn_notebook_type_id(notebooklist.getCn_notebook_type_id());
                List<Note> notelist = noteDao.findNoteByNoteBookId(notebooklist.getCn_notebook_id());
                noteListVo.setNoteList(notelist);
                int count = noteDao.countByNoteBookId(notebooklist.getCn_notebook_id());
                noteListVo.setNoteCount(count);
                noteList.add(noteListVo);
            }
            return ServerResponse.createBySuccess("笔记本列表", noteList);
        }
    }

    /*
    删除笔记本
    参数包括
     */
    public ServerResponse deleteByNoteBookIdAndUserId(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId", "noteBookId");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，必须包括userId,noteBook");
        }

        if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("用户不能为空");
        } else if (StringUtils.isBlank(map.get("noteBookId").toString())) {
            return ServerResponse.createByErrorMessage("笔记本id不能为空");
        } else {
            int userId = Integer.parseInt(map.get("userId").toString());
            int noteBookId = Integer.parseInt(map.get("noteBookId").toString());
            noteBookDao.deleteByNoteBookIdAndUserId(userId, noteBookId);
            return ServerResponse.createBySuccessMessags("删除成功");
        }
    }

    /*
    重命名笔记本
    参数包括
     */
    @Override
    public ServerResponse resetName(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId", "noteBookId", "newName");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，必须包括userId，noteBookId,newName");
        }
        if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("用户id不能为空");
        } else if (StringUtils.isBlank(map.get("noteBookId").toString())) {
            return ServerResponse.createByErrorMessage("笔记本id不能为空");
        } else if (StringUtils.isBlank(map.get("newName").toString())) {
            return ServerResponse.createByErrorMessage("新名称不能为空");
        } else {
            int notebookId = Integer.parseInt(map.get("noteBookId").toString());
            String newName = map.get("newName").toString();
            int userId = Integer.parseInt(map.get("userId").toString());
            int noteBookId = Integer.parseInt(map.get("noteBookId").toString());

            int count = noteBookDao.findNoteBookByNameAndUserId(map.get("newName").toString(), notebookId);
            if (count > 0) {
                return ServerResponse.createByErrorMessage("新名称已经存在");
            } else {
                noteBookDao.UpdateNoteBookName(userId, noteBookId, newName);
                return ServerResponse.createBySuccessMessags("重命名成功");
            }
        }
    }

    /*
    更新笔记本类型
    参数包括
     */
    @Override
    public ServerResponse setNoteBookType(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId", "noteBookId", "noteBookType");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，应包括userId,noteBookId,noteBookType");
        }
        if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("用户id不能为空");
        } else if (StringUtils.isBlank(map.get("noteBookId").toString())) {
            return ServerResponse.createByErrorMessage("笔记本id不能为空");
        }else if (StringUtils.isBlank(map.get("noteBookType").toString())){
            return ServerResponse.createByErrorMessage("笔记本类型不能为空");
        }
        else {
            int userId = Integer.parseInt(map.get("userId").toString());
            int noteBookId = Integer.parseInt(map.get("noteBookId").toString());
            int noteBookType = Integer.parseInt(map.get("noteBookType").toString());
            noteBookDao.updateNoteBookType(userId, noteBookId, noteBookType);
            return ServerResponse.createBySuccessMessags("修改成功");
        }
    }

    /*
     通过笔记本名查找笔记本
     参数包括
     */
    @Override
    public ServerResponse<List<NoteListVo>> findNoteBookByName(Map<String, Object> map) {

        List<String> list = Arrays.asList("userId", "searchText");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，应包含userId，searchText");
        }
        if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("用户id不能为空");
        } else if (StringUtils.isBlank(map.get("searchText").toString())) {
            return ServerResponse.createByErrorMessage("查找内容不能为空");
        } else {
            List<NoteListVo> noteList = new ArrayList<>();
            List<NoteBook> noteBookList = noteBookDao.SearchNoteBook(map.get("searchText").toString(), Integer.parseInt(map.get("userId").toString()),1);
            for (NoteBook notebooklist : noteBookList) {
                NoteListVo noteListVo = new NoteListVo();
                noteListVo.setCn_notebook_id(notebooklist.getCn_notebook_id());
                noteListVo.setCn_notebook_name(notebooklist.getCn_notebook_name());
                noteListVo.setCn_notebook_createTime(notebooklist.getCn_notebook_createTime());
                noteListVo.setCn_notebook_lastupdateTime(notebooklist.getCn_notebook_lastupdateTime());
                noteListVo.setCn_notebook_label_id(notebooklist.getCn_notebook_label_id());
                noteListVo.setCn_notebook_type_id(notebooklist.getCn_notebook_type_id());
                List<Note> notelist = noteDao.findNoteByNoteBookId(notebooklist.getCn_notebook_id());
                noteListVo.setNoteList(notelist);
                int countNote= noteDao.countByNoteBookId(notebooklist.getCn_notebook_id());
                noteListVo.setNoteCount(countNote);
                noteList.add(noteListVo);
            }
            return ServerResponse.createBySuccess("查询结果", noteList);
        }
    }
}
