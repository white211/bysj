package cn.white.bysj.admin.controller.admin.system;

import cn.white.bysj.admin.common.JsonResult;
import cn.white.bysj.admin.controller.BaseController;
import cn.white.bysj.admin.entity.Role;
import cn.white.bysj.admin.vo.NoteVo;
import cn.white.bysj.user.User;
import cn.white.bysj.admin.service.IRoleService;
import cn.white.bysj.admin.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/user")
public class IUserController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @RequestMapping(value = {"/index"})
    public String index(ModelMap modelMap) {
        Sort sort = new Sort(Sort.Direction.DESC,"cnUserCreateTime");
        Page<User> page = userService.findAll(getPageRequest(sort));
        modelMap.put("pageInfo", page);
        modelMap.put("type",0);
        return "admin/user/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(ModelMap map) {
        return "admin/user/form";
    }

    @RequestMapping(value = "/edit/{cn_user_id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer cn_user_id, ModelMap map) {
        User user = userService.find(cn_user_id);
        map.put("user", user);
        return "admin/user/form";
    }

    @RequestMapping(value = {"/edit"}, method = RequestMethod.POST)
    @ResponseBody
    public JsonResult edit(User user, ModelMap map) {
        try {
            userService.saveOrUpdate(user);
        } catch (Exception e) {
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    @RequestMapping(value = "/delete/{cn_user_id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delete(@PathVariable Integer cn_user_id, ModelMap map) {
        try {
            userService.delete(cn_user_id);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    @RequestMapping(value = "/grant/{cn_user_id}", method = RequestMethod.GET)
    public String grant(@PathVariable Integer cn_user_id, ModelMap map) {
        User user = userService.find(cn_user_id);
        map.put("user", user);

        Set<Role> set = user.getRoles();
        List<Integer> roleIds = new ArrayList<Integer>();
        for (Role role : set) {
            roleIds.add(role.getId());
        }
        map.put("roleIds", roleIds);

        List<Role> roles = roleService.findAll();
        map.put("roles", roles);
        return "admin/user/grant";
    }

    @ResponseBody
    @RequestMapping(value = "/grant/{cn_user_id}", method = RequestMethod.POST)
    public JsonResult grant(@PathVariable Integer cn_user_id, String[] roleIds, ModelMap map) {
        try {
            userService.grant(cn_user_id, roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

    @RequestMapping(value = "/updatePwd", method = RequestMethod.GET)
    public String updatePwd() {
        return "admin/user/updatePwd";
    }

    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updatePwd(String oldPassword, String password1, String password2) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Object principal = subject.getPrincipal();
            if (principal == null) {
                return JsonResult.failure("您尚未登录");
            }
            userService.updatePwd((User) principal, oldPassword, password1, password2);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.failure(e.getMessage());
        }
        return JsonResult.success();
    }

//    @GetMapping(value = "/findUserByTextInES")
//    public String findUserByTextInES(@RequestParam String text,ModelMap map){
//        Page<User> page = userService.findUserByTextInEs(text,getPageRequest());
//        map.put("pageInfo",page);
//        map.put("type",1);
//        map.put("text",text);
//        return "/admin/user/index";
//    }

    @RequestMapping(value = "/findUserByLike")
    public String findUserByLike(@RequestParam String text,ModelMap map){
        Page<User> page = userService.findUserByLike(text,getPageRequest());
        map.put("pageInfo",page);
        map.put("type",1);
        map.put("text",text);
        return "/admin/user/index";
    }

}
