package cn.white.bysj.notebook;

import cn.white.bysj.commons.ServerResponse;

import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-01-31 15:16
 */
public interface NoteBookService {

    ServerResponse newNoteBook(Map<String,Object> map);

    ServerResponse findAll(Map<String,Object> map);

    ServerResponse deleteByNoteBookIdAndUserId(Map<String,Object> map);

    ServerResponse resetName(Map<String,Object> map);

    ServerResponse setNoteBookType(Map<String,Object> map);

    ServerResponse findNoteBookByName(Map<String,Object> map);


}
