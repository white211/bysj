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
}
