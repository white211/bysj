package cn.white.bysj.note;

import cn.white.bysj.admin.dao.ITypeDao;
import cn.white.bysj.commons.Constant;
import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.label.LabelDao;
import cn.white.bysj.notebook.NoteBookDao;
import cn.white.bysj.user.User;
import cn.white.bysj.user.UserDao;
import cn.white.bysj.utils.MD5;
import cn.white.bysj.utils.ValidatorUtil;
import groovy.util.logging.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.SimpleDateFormat;
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

    @Autowired
    private UserDao userDao;

    @Autowired
    private ITypeDao iTypeDao;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private NoteBookDao noteBookDao;

    @Autowired
    private TransportClient client;

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
                    note.setCnNoteCreateTime(new Date());
                    note.setCnNoteUpdateTime(new Date());
                    Note note1 = noteDao.save(note);
                    map.put("id", note1.getCn_note_id());
                    addNoteToEs(map);
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
            return ServerResponse.createByErrorMessage("缺少参数，应该包括noteId,noteTypeId");
        }
        if (StringUtils.isBlank(map.get("noteId").toString())) {
            return ServerResponse.createByErrorMessage("笔记id不能为空");
        } else if (StringUtils.isBlank(map.get("noteTypeId").toString())) {
            return ServerResponse.createByErrorMessage("笔记类型不能为空");
        } else {
            try {
                noteDao.updateNoteTypeId(Integer.parseInt(map.get("noteTypeId").toString()), Integer.parseInt(map.get("noteId").toString()));
                updateNoteTypeToEs(map);
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
            desc = "";
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
                note.setCnNoteUpdateTime(new Date());
                Note note1 = noteDao.save(note);
                this.updateNoteToEs(map);
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
            return ServerResponse.createByErrorMessage("笔记id不能为空");
        } else {
            Note note;
            try {
                if(map.get("type").toString().equals("1")){
                     note = noteDao.findNoteByIdAndTypeId(Integer.parseInt(map.get("noteId").toString()));
                     return ServerResponse.createBySuccess("查询成功",note);
                }else if (map.get("type").toString().equals("2")){
                     note = noteDao.findNoteByIdAndCnNoteIsShare(Integer.parseInt(map.get("noteId").toString()));
                    return ServerResponse.createBySuccess("查询成功", note);
                }else{
                    return ServerResponse.createByErrorMessage("未知错误,查询失败");
                }
            } catch (Exception e) {
                logger.error("通过笔记id和类型id查找笔记，失败");
                return ServerResponse.createByErrorMessage("通过笔记id和类型id查找笔记，失败");
            }
        }
    }

    /**
     * TODO: 通过类型id查找笔记
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
                return ServerResponse.createBySuccess("笔记收藏列表", noteList);
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
                if (note == null) {
                    return ServerResponse.createByErrorMessage("笔记不存在");
                }
                int NoteRead = note.getCn_note_read() + 1;
                noteDao.updateNoteReadById(Integer.parseInt(map.get("noteId").toString()), NoteRead);
                updateReadToEs(Integer.parseInt(map.get("noteId").toString()), NoteRead);
                return ServerResponse.createBySuccessMessags("修改成功");
            } catch (Exception e) {
                logger.error("添加阅读量出错");
                return ServerResponse.createByErrorMessage("添加阅读量出错");
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
                deleteNoteToEs(String.valueOf(noteId));
                return ServerResponse.createBySuccessMessags("删除成功");
            } catch (Exception e) {
                logger.error("删除失败");
                return ServerResponse.createByErrorMessage("删除失败");
            }
        }
    }

    /**
     * TODO: 通过笔记本id查找笔记
     *
     * @param "bookId"
     * @return
     * @throws
     * @author white
     * @date 2018-04-04 13:43
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
                return ServerResponse.createBySuccess("笔记列表", noteList);
            } catch (Exception e) {
                logger.error("查找失败");
                return ServerResponse.createByErrorMessage("查找失败");
            }
        }
    }

    /**
     * TODO: 通过标签id查找笔记
     *
     * @param "labelId"
     * @return
     * @throws
     * @author white
     * @date 2018-04-04 13:43
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
                return ServerResponse.createBySuccess("笔记列表", noteList);
            } catch (Exception e) {
                logger.error("查找失败");
                return ServerResponse.createByErrorMessage("查找失败");
            }
        }
    }

    /**
     * TODO: 修改加解密的状态
     *
     * @return
     * @throws
     * @author white
     * @date 2018-05-01 11:07
     */
    @Override
    public ServerResponse updateEncrypt(Map<String, Object> map) {
        List<String> list = Arrays.asList("noteId", "encryptType");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，必须包括noteId,encryptType");
        }
        if (StringUtils.isBlank(map.get("noteId").toString())) {
            return ServerResponse.createByErrorMessage("笔记id不能为空");
        } else if (StringUtils.isBlank(map.get("encryptType").toString())) {
            return ServerResponse.createByErrorMessage("修改类型不能为空");
        } else {
            try {
                int noteId = Integer.parseInt(map.get("noteId").toString());
                int typeId = Integer.parseInt(map.get("encryptType").toString());
                noteDao.updateEncrypt(noteId, typeId);
                this.updateEncryptToEs(noteId, typeId);
                logger.info("更新加解密成功");
                return ServerResponse.createBySuccessMessags("更新加解密成功");
            } catch (Exception e) {
                logger.error("更新加解密失败-----服务出现异常");
                return ServerResponse.createByErrorMessage("更新加解密失败-----服务出现异常");
            }
        }
    }

    /**
     * TODO: 判断阅读密码是否输入正确
     *
     * @return
     * @throws
     * @author white
     * @date 2018-05-01 13:20
     */
    @Override
    public ServerResponse checkReadPassword(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId", "readPass");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，userId,readPass");
        }
        if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("笔记id不能为空");
        } else if (StringUtils.isBlank(map.get("readPass").toString())) {
            return ServerResponse.createByErrorMessage("阅读密码不能为空");
        } else {
            try {
                User user = userDao.findOne(Integer.parseInt(map.get("userId").toString()));
                if (user.getCnNoteReadPassword().equals(MD5.md5(map.get("readPass").toString()))) {
                    return ServerResponse.createBySuccess("密码正确");
                } else {
                    return ServerResponse.createByErrorMessage("密码错误");
                }
            } catch (Exception e) {
                logger.error("判断阅读密码出错---服务出现异常");
                return ServerResponse.createByErrorMessage("判断阅读密码出错---服务出现异常");
            }
        }
    }

    /**
     * TODO: 修改笔记分享的状态
     *
     * @param
     * @return
     * @throws
     * @author white
     * @date 2018-05-01 16:16
     */
    @Override
    public ServerResponse updateNoteIsShare(Map<String, Object> map) {
        List<String> list = Arrays.asList("noteId", "type");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，userId,type");
        }
        if (StringUtils.isBlank(map.get("noteId").toString())) {
            return ServerResponse.createByErrorMessage("笔记id不能为空");
        } else if (StringUtils.isBlank(map.get("type").toString())) {
            return ServerResponse.createByErrorMessage("修改类型不能为空");
        } else {
            try {
                int noteId  = Integer.parseInt(map.get("noteId").toString());
                int type = Integer.parseInt(map.get("type").toString());
                noteDao.updateNoteIsShare(noteId,type);
                return ServerResponse.createBySuccessMessags("修改笔记状态正确");
            } catch (Exception e) {
                logger.error("修改笔记分享状态出错---服务器出现异常");
                return ServerResponse.createByErrorMessage("修改笔记分享状态出错---服务器出现异常");
            }
        }
    }

    /***********************************在elasticsearch上面的相关操作***************************************/
    /*#####################################################################################################*/

    /**
     * TODO: 将笔记存放到es上面
     *
     * @return
     * @throws
     * @author white
     * @date 2018-04-17 13:25
     */
    public ServerResponse addNoteToEs(Map<String, Object> map) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String email = "";
        String bookname = "";
        String typename = "";
        String labelname = "";
        String noteRead = "0";

        email = userDao.findOne(Integer.parseInt(map.get("userId").toString())).getCn_user_email();
        typename = iTypeDao.findOne(1).getCn_type_name();
        if (!StringUtils.isBlank(map.get("noteBookId").toString())) {
            bookname = noteBookDao.findOne(Integer.parseInt(map.get("noteBookId").toString())).getCn_notebook_name();
        }
        if (!StringUtils.isBlank(map.get("noteLabel").toString())) {
            labelname = labelDao.findOne(Integer.parseInt(map.get("noteLabel").toString())).getCn_label_name();
        }

        try {
            XContentBuilder content = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("cnNoteTitle", map.get("noteTitle").toString())
                    .field("cnUserEmail", email)
                    .field("cnNoteType", typename)
                    .field("cnNoteContent", map.get("noteContent").toString())
                    .field("cnNoteBookName", bookname)
                    .field("cnNoteLabelName", labelname)
                    .field("cnNoteCreateTime", sdf.format(new Date()))
                    .field("cnNoteRead", noteRead)
                    .field("cnNoteIsEncrypt", 1)
                    .endObject();
            IndexResponse response = this.client.prepareIndex(Constant.ES_INDEX_NAME, Constant.ES_TYPE_NAME, map.get("id").toString())
                    .setSource(content)
                    .get();
            return ServerResponse.createBySuccess(response.getId(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorCodeMessage(Integer.parseInt(HttpStatus.INTERNAL_SERVER_ERROR.toString()), "添加到es失败");
        }

    }


    /**
     * TODO: 修改es某条笔记
     *
     * @param "userId","noteBookId","noteId","noteContent","noteTitle", "noteDesc","noteLabel"
     * @return
     * @throws
     * @author white
     * @date 2018-04-17 18:53
     */
    public ServerResponse updateNoteToEs(Map<String, Object> map) {
        String email = "";
        String bookname = "";
        String typename = "";
        String labelname = "";
        String noteRead = "0";
        if (!StringUtils.isBlank(map.get("noteBookId").toString())) {
            bookname = noteBookDao.findOne(Integer.parseInt(map.get("noteBookId").toString())).getCn_notebook_name();
        }
        if (!StringUtils.isBlank(map.get("noteLabel").toString())) {
            labelname = labelDao.findOne(Integer.parseInt(map.get("noteLabel").toString())).getCn_label_name();
        }
        if (!StringUtils.isBlank(map.get("userId").toString())) {
            email = userDao.findOne(Integer.parseInt(map.get("userId").toString())).getCn_user_email();
        }
        UpdateRequest updateRequest = new UpdateRequest(Constant.ES_INDEX_NAME, Constant.ES_TYPE_NAME, map.get("noteId").toString());
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject();
            content.field("cnNoteTitle", map.get("noteTitle").toString());
            content.field("cnNoteContent", map.get("noteContent").toString());
            content.field("cnNoteBookName", bookname);
            content.field("cnNoteLabelName", labelname);
            content.endObject();
            updateRequest.doc(content);
            UpdateResponse updateResponse = this.client.update(updateRequest).get();
            logger.info("es更新成功");
            return ServerResponse.createBySuccess(updateResponse.getResult().toString(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ServerResponse.createByErrorCodeMessage(Integer.parseInt(HttpStatus.INTERNAL_SERVER_ERROR.toString()), "es修改出错");
        }
    }

    /**
     * TODO: 彻底从es上删除笔记
     *
     * @return
     * @throws
     * @author white
     * @date 2018-04-17 19:53
     */
    public ServerResponse deleteNoteToEs(String noteId) {
        try {
            DeleteResponse response = client.prepareDelete(Constant.ES_INDEX_NAME, Constant.ES_TYPE_NAME, noteId).get();
            return ServerResponse.createBySuccess(response.getResult().toString(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ServerResponse.createByErrorMessage("彻底从es删除失败");
        }
    }


    /**
     * TODO: 更新es上笔记的类型
     *
     * @param "noteId，noteTypeId"
     * @return
     * @throws
     * @author white
     * @date 2018-04-17 19:58
     */
    public ServerResponse updateNoteTypeToEs(Map<String, Object> map) {
        String typename = "";

        if (!StringUtils.isBlank(map.get("noteTypeId").toString())) {
            typename = iTypeDao.findOne(Integer.parseInt(map.get("noteTypeId").toString())).getCn_type_name();
        }
        UpdateRequest updateRequest = new UpdateRequest(Constant.ES_INDEX_NAME, Constant.ES_TYPE_NAME, map.get("noteId").toString());
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject();
            content.field("cnNoteType", typename);
            content.endObject();
            updateRequest.doc(content);
            UpdateResponse updateResponse = this.client.update(updateRequest).get();
            logger.info("es更新笔记类型成功");
            return ServerResponse.createBySuccess(updateResponse.getResult().toString(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ServerResponse.createByErrorCodeMessage(Integer.parseInt(HttpStatus.INTERNAL_SERVER_ERROR.toString()), "es修改出错");
        }
    }

    /**
     * TODO: 更新es上面笔记的阅读量
     *
     * @return
     * @throws
     * @author white
     * @date 2018-04-17 20:56
     */
    public ServerResponse updateReadToEs(int noteId, int noteRead) {

        UpdateRequest updateRequest = new UpdateRequest(Constant.ES_INDEX_NAME, Constant.ES_TYPE_NAME, String.valueOf(noteId));
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject();
            content.field("cnNoteRead", noteRead);
            content.endObject();
            updateRequest.doc(content);
            UpdateResponse updateResponse = this.client.update(updateRequest).get();
            logger.info("es添加阅读量成功");
            return ServerResponse.createBySuccess(updateResponse.getResult().toString(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ServerResponse.createByErrorCodeMessage(Integer.parseInt(HttpStatus.INTERNAL_SERVER_ERROR.toString()), "es修改出错");
        }
    }

    /**
     * TODO: 更新es上面笔记的加解密状态
     *
     * @return
     * @throws
     * @author white
     * @date 2018-05-01 11:10
     */
    public ServerResponse updateEncryptToEs(int noteId, int encrypyType) {
        UpdateRequest updateRequest = new UpdateRequest(Constant.ES_INDEX_NAME, Constant.ES_TYPE_NAME, String.valueOf(noteId));
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject();
            content.field("cnNoteIsEncrypt", encrypyType);
            content.endObject();
            updateRequest.doc(content);
            UpdateResponse updateResponse = this.client.update(updateRequest).get();
            logger.info("es修改笔记加解密失败");
            return ServerResponse.createBySuccess(updateResponse.getResult().toString(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ServerResponse.createByErrorCodeMessage(Integer.parseInt(HttpStatus.INTERNAL_SERVER_ERROR.toString()), "es修改笔记加解密失败");
        }
    }


}
