package cn.white.bysj.admin.controller.admin.system;

import cn.white.bysj.admin.common.JsonResult;
import cn.white.bysj.admin.controller.BaseController;
import cn.white.bysj.admin.entity.Home;
import cn.white.bysj.admin.service.IHomeServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Create by @author white
 *
 * @date 2018-04-05 20:58
 */
@Controller
@RequestMapping(value = "admin/home")
public class IHomeController extends BaseController {

    @Autowired
    private IHomeServcie iHomeServcie;

    @RequestMapping(value = {"/index"})
    public String index(ModelMap map) {
        Sort sort = new Sort(Sort.Direction.DESC,"cnHomeCreateTime");
        Page<Home> page = iHomeServcie.findAll(getPageRequest(sort));
        map.put("pageInfo",page);
        return "/admin/home/index";
    }

    @GetMapping(value = "/add")
    public String add(ModelMap map){
        return "/admin/home/form";
    }

    @GetMapping(value = "/edit/{cn_home_id}")
    public String see(@PathVariable Integer cn_home_id, ModelMap map){
       Home home = iHomeServcie.find(cn_home_id);
       map.put("home",home);
       return "/admin/home/form";
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    public JsonResult edit(Home home){
       try{
           iHomeServcie.saveOrUpdate(home);
       }catch (Exception e){
           return JsonResult.failure(e.getMessage());
       }
       return JsonResult.success();
    }

    @PostMapping(value = "/delete/{cn_home_id}")
    public JsonResult delete(@PathVariable Integer cn_home_id,ModelMap map){
         try {
             iHomeServcie.delete(cn_home_id);
         }catch (Exception e){
             return JsonResult.failure(e.getMessage());
         }
         return JsonResult.success();
    }

    @PostMapping(value = "/use/{cn_home_id}")
    public JsonResult use(@PathVariable Integer cn_home_id,@PathVariable String type, ModelMap map){
         try{
            iHomeServcie.updateType(cn_home_id,type);
         }catch (Exception e){
             return JsonResult.failure(e.getMessage());
         }
         return JsonResult.success();
    }



}
