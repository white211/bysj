package cn.white.bysj.note;

import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.utils.ComponentHelper;
import cn.white.bysj.utils.Cors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-03-02 21:36
 */

@Controller
@RequestMapping(value = "/note")
public class NoteController extends Cors {

    @Autowired
    private NoteServiceImpl noteService;

    @RequestMapping(value = "newNote.do")
    @ResponseBody
    public ServerResponse newNote(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return noteService.newNote(map);
    }

    @RequestMapping(value = "deleteNote.do")
    @ResponseBody
    public ServerResponse deleteNote(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return noteService.deleteNote(map);
     }

    @RequestMapping(value = "findNoteByTitleOrContent.do")
    @ResponseBody
    public ServerResponse findNoteByTitleOrContent(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return noteService.findNoteByTitleOrContent(map);
    }



}
