package cn.white.bysj.note;

import cn.white.bysj.commons.ServerResponse;

import java.util.List;
import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-03-02 21:36
 */
public interface NoteService {
    //新建笔记
    ServerResponse newNote(Map<String,Object> map);

    //删除笔记
    ServerResponse updateNoteTypeId(Map<String,Object> map);

    //通过笔记标题或者内容查找笔记
    ServerResponse<List<Note>> findNoteByTitleOrContent(Map<String,Object> map);

    //笔记列表
    ServerResponse<List<Note>> noteList(Map<String,Object> map);

    //更新笔记
    ServerResponse updateNote(Map<String,Object> map);

    //笔记阅读量
    ServerResponse addRead(Map<String,Object> map);

    //通过笔记类型查找笔记
    ServerResponse findNoteByTypeId(Map<String,Object> map);

    //删除笔记通过笔记id
    ServerResponse deleteByNoteId(Map<String,Object> map);

    //通过笔记本id查找笔记
    ServerResponse findNoteByBookId(Map<String,Object> map);

    //通过标签id查找笔记
    ServerResponse findNoteByTagId(Map<String,Object> map);

    /**
     * 更新笔记加密解密状态
     * @param map
     * @return
     */
    ServerResponse updateEncrypt(Map<String,Object> map);

    /**
     * 判断输入的密码是否正确
     * @param map
     * @return
     */
    ServerResponse checkReadPassword(Map<String,Object> map);

    /**
     * 修改笔记分享的状态
     * @return
     */
    ServerResponse updateNoteIsShare(Map<String,Object> map);

    /**
     * 用户笔记分享获取笔记内容
     * @param map
     * @return
     */
    ServerResponse findNoteByIdFromShareNote(Map<String,Object> map);


    /*#################################################################################*/
    //-------------------------es上面相关操作-------------------------------------------

    ServerResponse addNoteToEs(Map<String, Object> map);//添加笔记

    ServerResponse updateNoteToEs(Map<String, Object> map);//更新笔记

    ServerResponse deleteNoteToEs(String noteId);//彻底删除笔记

    ServerResponse updateNoteTypeToEs(Map<String, Object> map);//更新笔记类型

    ServerResponse updateReadToEs(int noteId, int noteRead);//更新笔记阅读量

    ServerResponse updateEncryptToEs(int noteId, int encrypyType);//更新笔记加解密状态

    ServerResponse updateNoteTypeByNoteBookNameToEs(int bookType, int bookId);//更新笔记类型通过笔记本名称

    ServerResponse deleteNoteByNoteBookIdToEs(int bookId);//通过笔记本删除笔记

    ServerResponse deleteNoteByUserIdToEs(int userId);//通过笔记类型删除笔记

}
