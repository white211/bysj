package cn.white.bysj.admin.controller.admin.system;

import cn.white.bysj.admin.common.JsonResult;
import cn.white.bysj.admin.controller.BaseController;
import cn.white.bysj.admin.entity.Shield;
import cn.white.bysj.admin.service.IShieldService;
import cn.white.bysj.admin.vo.ShieldVo;
import cn.white.bysj.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Create by @author white
 *
 * @date 2018-04-01 22:51
 */
@Controller
@RequestMapping(value = "/admin/shield")
public class ShieldController extends BaseController {

    @Autowired
    private IShieldService iShieldService;

    @GetMapping(value = {"/index"})
    public String index(ModelMap map) {
        Sort sort =new Sort(Sort.Direction.DESC,"cnShieldCreateTime");
        Page<ShieldVo> page = iShieldService.findShield(getPageRequest(sort));
        map.put("pageInfo", page);
        return "admin/shield/index";
    }

    @GetMapping(value = "/edit/{cn_shield_id}")
    public String edit(@PathVariable Integer cn_shield_id, ModelMap map) {
        ShieldVo shieldVo = iShieldService.findOne(cn_shield_id);
        map.put("shield", shieldVo);
        return "admin/shield/form";
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    public JsonResult edit(Shield shield, ModelMap map) {
        try {
            iShieldService.saveOrUpdate(shield);
        } catch (Exception e) {
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    @GetMapping(value = "/add")
    public String add(ModelMap map){
        return "admin/shield/add";
    }

    @PostMapping(value = "/delete/{cn_shield_id}")
    @ResponseBody
    public JsonResult delete(@PathVariable Integer cn_shield_id, ModelMap map) {
        try {
            iShieldService.delete(cn_shield_id);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }


}
