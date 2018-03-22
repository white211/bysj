package cn.white.bysj.comment;

import cn.white.bysj.commons.ServerResponse;

import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-03-21 13:29
 */
public interface CommentService {

    ServerResponse newComment(Map<String,Object> map);

    ServerResponse deleteComment(Map<String,Object> map);

    ServerResponse CommentListByNoteId(Map<String,Object> map);

}
