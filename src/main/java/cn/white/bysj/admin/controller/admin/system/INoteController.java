package cn.white.bysj.admin.controller.admin.system;

import cn.white.bysj.user.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Create by @author white
 *
 * @date 2018-03-05 12:22
 */
@Controller
@RequestMapping(value = "/admin/note")
public class INoteController {

    @RequestMapping(value = {"/index"})
    public String index(ModelMap modelMap) {
//        Page<User> page = userService.findAll(getPageRequest());
//        modelMap.put("pageInfo", page);
        return "admin/note/index";
    }
}
