package cn.white.bysj.admin.controller.admin.system;

import cn.white.bysj.admin.common.JsonResult;
import cn.white.bysj.admin.controller.BaseController;
import cn.white.bysj.admin.service.IFeedBackService;
import cn.white.bysj.admin.vo.FeedBackVo;
import cn.white.bysj.feedback.Feedback;
import cn.white.bysj.note.Note;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Create by @author white
 *
 * @date 2018-04-05 11:27
 */
@Controller
@RequestMapping(value = "admin/feedback")
public class IFeedBackController extends BaseController {

    @Autowired
    private IFeedBackService iFeedBackService;

    @RequestMapping(value = {"/index"})
    public String index(ModelMap map){
        Sort sort = new Sort(Sort.Direction.DESC,"cnFeedbackCreateTime");
        Page<FeedBackVo> page = iFeedBackService.findFeedBack(getPageRequest());
        map.put("pageInfo",page);
        return "admin/feedback/index";
    }

    @GetMapping(value = "/see/{cn_feedback_id}")
    public String see(@PathVariable Integer cn_feedback_id, ModelMap modelMap){
        FeedBackVo feedBackVo = iFeedBackService.findOne(cn_feedback_id);
        modelMap.put("feedback",feedBackVo);
        return "admin/feedback/see";
    }



    @GetMapping(value = "/return/{cn_feedback_id}")
    public String Return(@PathVariable Integer cn_feedback_id, ModelMap modelMap){
        FeedBackVo feedBackVo = iFeedBackService.findOne(cn_feedback_id);
        modelMap.put("feedback",feedBackVo);
        return "admin/feedback/return";
    }


    @PostMapping(value = "/return")
    public JsonResult Return(FeedBackVo feedBackVo, ModelMap modelMap){

        try {
            iFeedBackService.UpdateAndSendEmail(feedBackVo);
        } catch (Exception e) {
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    @PostMapping(value = "/delete/{cn_feedback_id}")
    @ResponseBody
    public JsonResult delete(@PathVariable Integer cn_feedback_id, ModelMap map) {
        try {
            iFeedBackService.delete(cn_feedback_id);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }



}
