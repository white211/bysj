package cn.white.bysj.comment;

import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.utils.ComponentHelper;
import cn.white.bysj.utils.Cors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-03-21 13:29
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController extends Cors{

   @Autowired
   private CommentService commentService;

   @RequestMapping(value = "newComment.do")
   @ResponseBody
   public ServerResponse newComment(HttpServletRequest request){
       Map<String,Object> map = ComponentHelper.requestToMap(request);
       return commentService.newComment(map);
   }

    @RequestMapping(value = "deleteComment.do")
    @ResponseBody
    public ServerResponse deleteComment(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return commentService.deleteComment(map);
    }

    @RequestMapping(value = "CommentListByNoteId.do")
    @ResponseBody
    public ServerResponse CommentListByNoteId(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return commentService.CommentListByNoteId(map);
    }



}
