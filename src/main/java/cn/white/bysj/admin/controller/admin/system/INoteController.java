package cn.white.bysj.admin.controller.admin.system;

import cn.white.bysj.admin.common.JsonResult;
import cn.white.bysj.admin.controller.BaseController;
import cn.white.bysj.admin.service.INoteService;
import cn.white.bysj.admin.vo.NoteVo;
import cn.white.bysj.note.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Create by @author white
 *
 * @date 2018-03-05 12:22
 */
@Controller
@RequestMapping(value = "/admin/note")
public class INoteController extends BaseController {

    @Autowired
    private INoteService iNoteService;

    @RequestMapping(value = {"/index"})
    public String index(ModelMap modelMap) {
        Page<NoteVo> page = iNoteService.findNote(getPageRequest());
        modelMap.put("pageInfo", page);
        return "admin/note/index";
    }

    @GetMapping(value = "/see/{cn_note_id}")
    public String see(@PathVariable Integer cn_note_id,ModelMap modelMap){
         Note note = iNoteService.find(cn_note_id);
         modelMap.put("note",note);
         return "admin/note/see";
    }

    @PostMapping(value = "/delete/{cn_note_id}")
    @ResponseBody
    public JsonResult delete(@PathVariable Integer cn_note_id, ModelMap map) {
        try {
            iNoteService.delete(cn_note_id);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

}
