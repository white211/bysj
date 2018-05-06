package cn.white.bysj.home;

import cn.white.bysj.admin.controller.BaseController;
import cn.white.bysj.admin.entity.Home;
import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.utils.ComponentHelper;
import cn.white.bysj.utils.Cors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-04-09 14:08
 */
@Controller
@RequestMapping("home")
public class HomeController extends Cors
//        extends BaseController
{

    @Autowired
    private HomeService homeService;

    @RequestMapping(value = "/findHome.do")
    @ResponseBody
    public ServerResponse findHome(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return  homeService.findHomeList();
    }


}
