package cn.white.bysj.notebook;

import cn.white.bysj.admin.controller.BaseController;
import cn.white.bysj.admin.service.INoteBookService;
import cn.white.bysj.admin.service.INoteService;
import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.note.Note;
import cn.white.bysj.utils.ComponentHelper;
import cn.white.bysj.utils.Cors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-01-31 15:17
 */
@RestController
@RequestMapping("/notebook")
public class NoteBookController extends Cors {

    @Autowired
    private NoteBookService noteBookService;

    @RequestMapping(value = "newNoteBook.do")
    @ResponseBody
    public ServerResponse newNoteBook(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return noteBookService.newNoteBook(map);
    }

    @RequestMapping(value = "noteBookList.do")
    @ResponseBody
    public ServerResponse noteBookList(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return noteBookService.findAll(map);
    }

    @RequestMapping(value = "deleteByNoteBookId.do")
    @ResponseBody
    public ServerResponse deleteByNoteBookIdAndUserId(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return noteBookService.deleteByNoteBookId(map);
    }

    @RequestMapping(value = "setNoteBookType.do")
    @ResponseBody
    public ServerResponse setNoteBookType(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return noteBookService.setNoteBookType(map);
    }

    @RequestMapping(value = "resetName.do")
    @ResponseBody
    public ServerResponse resetName(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return noteBookService.resetName(map);
    }

    @RequestMapping(value = "findNoteBook.do")
    @ResponseBody
    public ServerResponse findNoteBook(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return noteBookService.findNoteBookByName(map);
    }

    @RequestMapping(value = "findNoteBookByTypeId.do")
    @ResponseBody
    public ServerResponse findNoteBookByTypeId(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return noteBookService.findNoteBookByTypeId(map);
    }

}
