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
    ServerResponse deleteNote(Map<String,Object> map);

    //通过笔记标题或者内容查找笔记
    ServerResponse<List<Note>> findNoteByTitleOrContent(Map<String,Object> map);

}
