package cn.white.bysj.note;

import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.utils.ValidatorUtil;
import groovy.util.logging.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private static Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    /**
     * TODO: 新增笔记、更新笔记
     *
     * @return cn.white.bysj.commons.ServerResponse<cn.white.bysj.note.Note>
     * @throws
     * @author white
     * @date 2018-03-17 10:25
     * @Params "noteBookId","noteContent","noteTitle","noteDesc","noteLabel","userId","noteId"
     */
    @Override
    public ServerResponse<Note> newNote(Map<String, Object> map) {
        Integer noteBookId;
        String desc;
        Integer noteLabelId;

        List<String> list = Arrays.asList("noteId", "noteBookId", "noteContent", "noteTitle", "noteDesc", "noteLabel", "userId");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，应包括noteBookId,noteContent,noteTitle,noteDesc,noteLabel");
        }
        if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("用户id不能为空");
        }
        if (StringUtils.isBlank(map.get("noteBookId").toString())) {
            noteBookId = null;
        } else {
            noteBookId = Integer.parseInt(map.get("noteBookId").toString());
        }

        if (StringUtils.isBlank(map.get("noteDesc").toString())) {
            desc = null;
        } else {
            desc = map.get("noteDesc").toString();
        }

        if (StringUtils.isBlank(map.get("noteLabel").toString())) {
            noteLabelId = null;
        } else {
            noteLabelId = Integer.parseInt(map.get("noteLabel").toString());
        }

        if (StringUtils.isBlank(map.get("noteTitle").toString())) {
            return ServerResponse.createByErrorMessage("笔记标题不能为空");
        } else if (StringUtils.isBlank(map.get("noteContent").toString())) {
            return ServerResponse.createByErrorMessage("笔记内容不能为空");
        } else {
            try {
                if (StringUtils.isBlank(map.get("noteId").toString())) {
                    Note note = new Note();
                    note.setCn_user_id(Integer.parseInt(map.get("userId").toString()));
                    note.setCn_note_title(map.get("noteTitle").toString());
                    note.setCn_note_content(map.get("noteContent").toString());
                    note.setCn_note_book_id(noteBookId);
                    note.setCn_note_desc(desc);
                    note.setCn_note_label_id(noteLabelId);
                    note.setCn_note_type_id(1);
                    note.setCn_note_createTime(new Date());
                    note.setCn_note_updateTime(new Date());
                    noteDao.save(note);
                    return ServerResponse.createBySuccess("新建笔记成功", note);
                } else {
                    return updateNote(map);
                }
            } catch (Exception e) {
                logger.error("服务出现异常");
                e.printStackTrace();
                return ServerResponse.createByErrorMessage("服务出现异常");
            }

        }
    }

    /**
     * TODO: 删除，收藏(更新笔记的类型id)
     *
     * @return
     * @throws
     * @author white
     * @date 2018-03-17 10:31
     * @params "noteId，noteTypeId"
     */
    @Override
    public ServerResponse updateNoteTypeId(Map<String, Object> map) {
        List<String> list = Arrays.asList("noteId", "noteTypeId");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，应该包括noteID,noteTypeId");
        }
        if (StringUtils.isBlank(map.get("noteId").toString())) {
            return ServerResponse.createByErrorMessage("笔记id不能为空");
        } else if (StringUtils.isBlank(map.get("noteTypeId").toString())) {
            return ServerResponse.createByErrorMessage("笔记类型不能为空");
        } else {
            try {
                noteDao.updateNoteTypeId(Integer.parseInt(map.get("noteTypeId").toString()), Integer.parseInt(map.get("noteId").toString()));
                return ServerResponse.createBySuccessMessags("操作成功");
            } catch (Exception e) {
                logger.error("服务出现异常");
                return ServerResponse.createByErrorMessage("服务出现异常");
            }

        }
    }

    /**
     * TODO: 通过笔记标题或者笔记内容查找某用户笔记
     *
     * @return
     * @throws
     * @author white
     * @date 2018-03-17 10:32
     * @Params "searchText,userId,noteBookId"
     */
    @Override
    public ServerResponse<List<Note>> findNoteByTitleOrContent(Map<String, Object> map) {
        List<String> list = Arrays.asList("searchText", "userId", "noteBookId");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，应该包括searchText,userId,noteBookId");
        }
        if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("用户id不能为空");
        }

        if (StringUtils.isBlank(map.get("searchText").toString())) {
            return ServerResponse.createByErrorMessage("搜索内容不能为空");
        } else {
            try {
                int userId = Integer.parseInt(map.get("userId").toString());
                int noteBookId = Integer.parseInt(map.get("noteBookId").toString());
                List<Note> noteList = noteDao.findNoteByTitleOrContent(map.get("searchText").toString(), userId, noteBookId);
                return ServerResponse.createBySuccess("搜索结果", noteList);
            } catch (Exception e) {
                logger.error("服务出现异常");
                return ServerResponse.createByErrorMessage("服务出现异常");
            }

        }
    }

    /**
     * TODO: 笔记列表
     *
     * @return
     * @throws
     * @author white
     * @date 2018-03-17 10:48
     * @Params "userId"
     */
    @Override
    public ServerResponse<List<Note>> noteList(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数包括用户id");
        }
        if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("用户id不能为空");
        } else {
            try {
                int userId = Integer.parseInt(map.get("userId").toString());
                List<Note> noteList = noteDao.findNoteByUserId(userId);
                return ServerResponse.createBySuccess("笔记列表", noteList);
            } catch (Exception e) {
                logger.error("服务出现异常");
                return ServerResponse.createByErrorMessage("服务出现异常");
            }

        }
    }

    /**
     * TODO: 更新笔记内容
     *
     * @param "userId","noteBookId","noteId","noteContent","noteTitle", "noteDesc","noteLabel"
     * @return
     * @throws
     * @author white
     * @date 2018-03-17 11:17
     */
    @Override
    public ServerResponse<Note> updateNote(Map<String, Object> map) {
        Integer noteBookId;
        String desc;
        Integer noteLabelId;
        List<String> list = Arrays.asList("userId", "noteBookId", "noteId", "noteContent", "noteTitle", "noteDesc", "noteLabel");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，必须包括userId,noteBookId,noteId,noteContent,noteTitle, noteDesc");
        }
        if (StringUtils.isBlank(map.get("noteBookId").toString())) {
            noteBookId = null;
        } else {
            noteBookId = Integer.parseInt(map.get("noteBookId").toString());
        }

        if (StringUtils.isBlank(map.get("noteDesc").toString())) {
            desc = null;
        } else {
            desc = map.get("noteDesc").toString();
        }

        if (StringUtils.isBlank(map.get("noteLabel").toString())) {
            noteLabelId = null;
        } else {
            noteLabelId = Integer.parseInt(map.get("noteLabel").toString());
        }

        if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("用户id不能为空");
        } else if (StringUtils.isBlank(map.get("noteId").toString())) {
            return ServerResponse.createByErrorMessage("笔记id不能为空");
        } else if (StringUtils.isBlank(map.get("noteTitle").toString())) {
            return ServerResponse.createByErrorMessage("笔记标题不能为空");
        } else {
            try {
                Note note = noteDao.findOne(Integer.parseInt(map.get("noteId").toString()));
                note.setCn_user_id(Integer.parseInt(map.get("userId").toString()));
                note.setCn_note_id(Integer.parseInt(map.get("noteId").toString()));
                note.setCn_note_book_id(noteBookId);
                note.setCn_note_title(map.get("noteTitle").toString());
                note.setCn_note_content(map.get("noteContent").toString());
                note.setCn_note_label_id(noteLabelId);
                note.setCn_note_desc(desc);
                note.setCn_note_type_id(1);
                note.setCn_note_updateTime(new Date());
                noteDao.save(note);
                return ServerResponse.createBySuccess("更新成功", note);
            } catch (Exception e) {
                logger.error("服务出现异常");
                return ServerResponse.createByErrorMessage("服务出现异常");
            }

        }
    }

    /**
     * TODO: 通过id查找笔记
     *
     * @param "noteId"
     * @return
     * @throws
     * @author white
     * @date 2018-03-17 21:54
     */
    public ServerResponse<Note> findNoteById(Map<String, Object> map) {
        List<String> list = Arrays.asList("noteId");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，必须包括noteId");
        }
        if (StringUtils.isBlank(map.get("noteId").toString())) {
            return ServerResponse.createByErrorMessage("笔记本id不能为空");
        } else {
            try {
                Note note = noteDao.findOne(Integer.parseInt(map.get("noteId").toString()));
                return ServerResponse.createBySuccess("查询成功", note);
            } catch (Exception e) {
                logger.error("服务出现异常");
                return ServerResponse.createByErrorMessage("服务出现异常");
            }

        }
    }

    /**
     * TODO: 通过typeid查找笔记本
     *
     * @param "userId,typeId"
     * @return
     * @throws
     * @author white
     * @date 2018-03-23 11:10
     */
    @Override
    public ServerResponse<List<Note>> findNoteByTypeId(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId", "typeId");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，应包括userId,typeId");
        }
        if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("用户id不能为空");
        } else if (StringUtils.isBlank(map.get("typeId").toString())) {
            return ServerResponse.createByErrorMessage("笔记类型不能为空");
        } else {
            try {
                int userId = Integer.parseInt(map.get("userId").toString());
                int typeId = Integer.parseInt(map.get("typeId").toString());
                List<Note> noteList = noteDao.findNoteByTypeId(userId, typeId);
                return ServerResponse.createBySuccess("笔记收藏列", noteList);
            } catch (Exception e) {
                logger.error("查找失败");
                return ServerResponse.createByErrorMessage("查找失败");
            }
        }

    }

    /**
     * TODO: 修改阅读量
     *
     * @param “noteId”
     * @return
     * @throws
     * @author white
     * @date 2018-03-21 13:57
     */
    @Override
    public ServerResponse addRead(Map<String, Object> map) {
        List<String> list = Arrays.asList("noteId");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，必须包括noteId");
        }

        if (StringUtils.isBlank(map.get("noteId").toString())) {
            return ServerResponse.createByErrorMessage("笔记本id不能为空");
        } else {
            try {
                Note note = noteDao.findOne(Integer.parseInt(map.get("noteId").toString()));
                int NoteRead = note.getCn_note_read() + 1;
                noteDao.updateNoteReadById(Integer.parseInt(map.get("noteId").toString()), NoteRead);
                return ServerResponse.createBySuccessMessags("修改成功");
            } catch (Exception e) {
                logger.error("服务出现异常");
                return ServerResponse.createByErrorMessage("服务出现异常");
            }

        }
    }

    /**
     * TODO: 彻底删除
     *
     * @param "noteId"
     * @return
     * @throws
     * @author white
     * @date 2018-03-23 15:08
     */
    @Override
    public ServerResponse deleteByNoteId(Map<String, Object> map) {
        List<String> list = Arrays.asList("noteId");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，必须包括noteId");
        }
        if (StringUtils.isBlank(map.get("noteId").toString())) {
            return ServerResponse.createByErrorMessage("笔记id不能为空");
        } else {
            try {
                int noteId = Integer.parseInt(map.get("noteId").toString());
                noteDao.deleteByNoteId(noteId);
                return ServerResponse.createBySuccessMessags("删除成功");
            } catch (Exception e) {
                logger.error("删除失败");
                return ServerResponse.createByErrorMessage("删除失败");
            }
        }
    }

    /**
     * TODO: 通过笔记本id查找笔记
     * @author white
     * @date 2018-04-04 13:43
       @param "bookId"
     * @return
     * @throws
     */
    @Override
    public ServerResponse findNoteByBookId(Map<String, Object> map) {
        List<String> list = Arrays.asList("bookId");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，必须包括bookId");
        }
        if (StringUtils.isBlank(map.get("bookId").toString())) {
            return ServerResponse.createByErrorMessage("笔记本id不能为空");
        } else {
            try {
              int bookId = Integer.parseInt(map.get("bookId").toString());
              List<Note> noteList = noteDao.findNoteByNoteBookId(bookId);
              return ServerResponse.createBySuccess("笔记列表",noteList);
            }catch (Exception e){
                logger.error("查找失败");
                return ServerResponse.createByErrorMessage("查找失败");
            }
        }
    }

    /**
     * TODO: 通过标签id查找笔记
     * @author white
     * @date 2018-04-04 13:43
       @param "labelId"
      * @return
     * @throws
     */
    @Override
    public ServerResponse findNoteByTagId(Map<String, Object> map) {
        List<String> list = Arrays.asList("labelId");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，必须包括labelId");
        }
        if (StringUtils.isBlank(map.get("labelId").toString())) {
            return ServerResponse.createByErrorMessage("标签id不能为空");
        } else {
            try {
                int labelId = Integer.parseInt(map.get("labelId").toString());
                List<Note> noteList = noteDao.findNoteByLabelId(labelId);
                return ServerResponse.createBySuccess("笔记列表",noteList);
            }catch (Exception e){
                logger.error("查找失败");
                return ServerResponse.createByErrorMessage("查找失败");
            }
        }
    }


}
