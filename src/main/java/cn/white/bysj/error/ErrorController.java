package cn.white.bysj.error;

import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.utils.Cors;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Create by @author white
 *
 * @date 2018-05-04 12:29
 */
@Controller
@RequestMapping(value = "/error")
public class ErrorController extends Cors {

    @RequestMapping(value = "noAuth.do")
    @ResponseBody
    public ServerResponse noAuth(HttpServletRequest request){
        return ServerResponse.createByErrorCodeMessage(403,"没有权限访问 Forbidden");
    }

    @RequestMapping(value = "timeout.do")
    @ResponseBody
    public ServerResponse timeout(HttpServletRequest request){
        return ServerResponse.createByErrorCodeMessage(401,"token已经过期");
    }


}
