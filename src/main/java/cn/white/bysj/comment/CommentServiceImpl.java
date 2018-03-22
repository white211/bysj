package cn.white.bysj.comment;

import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.user.UserService;
import cn.white.bysj.utils.ValidatorUtil;
import com.sun.tools.corba.se.idl.StringGen;
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
 * @date 2018-03-21 13:29
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    private static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    /**
     * TODO: 添加评论
     *
     * @param "userId,noteId,commentContent,userEmail,userAvatar,userNickName"
     * @return
     * @throws
     * @author white
     * @date 2018-03-21 13:39
     */
    public ServerResponse newComment(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId", "noteId", "commentContent", "userEmail", "userAvatar", "userNickName");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，包括userId,noteId,commentContent，userEmail，userAvatar，userNickName");
        }

        if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("用户id不能为空");
        } else if (StringUtils.isBlank(map.get("noteId").toString())) {
            return ServerResponse.createByErrorMessage("笔记id不能为空");
        } else if (StringUtils.isBlank(map.get("commentContent").toString())) {
            return ServerResponse.createByErrorMessage("评论内容不能为空");
        } else if (StringUtils.isBlank(map.get("userEmail").toString())) {
            return ServerResponse.createByErrorMessage("评论人邮箱不能为空");
        } else {
            try {
                Comment comment = new Comment();
                comment.setCn_note_id(Integer.parseInt(map.get("noteId").toString()));
                comment.setCn_user_id(Integer.parseInt(map.get("userId").toString()));
                comment.setCn_comment_content(map.get("commentContent").toString());
                comment.setCn_comment_creatTime(new Date());
                comment.setCn_user_avatar(ValidatorUtil.toString(map.get("userAvatar")));
                comment.setCn_user_email(map.get("userEmail").toString());
                comment.setCn_user_nickName(ValidatorUtil.toString(map.get("userNickName")));
                commentDao.save(comment);
                return ServerResponse.createBySuccessMessags("评论成功");
            } catch (Exception e) {
                logger.error("添加出错");
                return ServerResponse.createByErrorMessage("服务出现异常");
            }

        }
    }

    /**
     * TODO: 删除评论
     *
     * @param "commentId"
     * @return
     * @throws
     * @author white
     * @date 2018-03-21 13:42
     */
    public ServerResponse deleteComment(Map<String, Object> map) {
        List<String> list = Arrays.asList("commentId");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，包括commentId");
        }
        if (StringUtils.isBlank(map.get("commentId").toString())) {
            return ServerResponse.createByErrorMessage("评论id不能为空");
        } else {
            try {
                int commentId = Integer.parseInt(map.get("commentId").toString());
                commentDao.delete(commentId);
                return ServerResponse.createByErrorMessage("删除成功");
            } catch (Exception e) {
                logger.error("删除失败");
                return ServerResponse.createByErrorMessage("服务出现异常");
            }
        }
    }

    /**
     * TODO: 通过笔记id查找评论
     *
     * @param "noteId"
     * @return
     * @throws
     * @author white
     * @date 2018-03-21 13:43
     */
    public ServerResponse<List<Comment>> CommentListByNoteId(Map<String, Object> map) {
        List<String> list = Arrays.asList("noteId");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，包括noteId");
        }
        if (StringUtils.isBlank(map.get("noteId").toString())) {
            return ServerResponse.createByErrorMessage("笔记id不能为空");
        } else {
            try {
                int noteId = Integer.parseInt(map.get("noteId").toString());
                List<Comment> list1 = commentDao.findAllByNoteId(noteId);
                return ServerResponse.createBySuccess("评论列表",list1);
            } catch (Exception e) {
                logger.error("查找失败");
                return ServerResponse.createByErrorMessage("服务出现异常");
            }
        }
    }


}
