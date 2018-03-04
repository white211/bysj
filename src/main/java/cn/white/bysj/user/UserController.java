package cn.white.bysj.user;

import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.utils.ComponentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Create by @author white
 *
 * @date 2017-12-28 16:04
 */
@CrossOrigin(origins = "http://localhost:8082", maxAge = 3600)
@Controller
@RequestMapping(value = "user/")
public class UserController {
    @Autowired
    private UserService userService;

    //登录
    @RequestMapping(value = "login.do")
    @ResponseBody
    public ServerResponse<User> login(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return userService.login(request.getSession(), map);
    }
    //退出登录
    @RequestMapping(value = "logout.do")
    @ResponseBody
    public ServerResponse<User> logout(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return userService.logout(map, request.getSession());
    }
    //注册
    @RequestMapping(value = "register.do")
    @ResponseBody
    public ServerResponse<User> register(HttpServletRequest request) throws InterruptedException, ExecutionException {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return userService.register(map).get();
    }
    //邮箱激活
    @RequestMapping(value = "activate.do")
    @ResponseBody
    public ServerResponse active(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return userService.updateUserState(map);
    }
    //获取手机验证码并发送
    @RequestMapping(value = "sendCheckNum.do")
    @ResponseBody
    public ServerResponse sendCheckNum(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return userService.sendCheckNum(map);
    }
    //判断是否存在已经注册并激活的邮箱
    @RequestMapping(value = "checkEmailIsExist.do")
    @ResponseBody
    public ServerResponse checkEmailIsExist(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return userService.checkEmailIsExist(map);
    }
    //验证手机验证码
    @RequestMapping(value = "checkTelephoneCheckNum.do")
    @ResponseBody
    public ServerResponse checkTelephoneCheckNum(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return userService.checkTelephoneCheckNum(map);
    }
    //未登陆状态下-忘记密码
    @RequestMapping(value = "fogetResetPassword.do")
    @ResponseBody
    public ServerResponse forgetResetPassword(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return userService.forgetResetPassword(map);
    }

    //登陆状态下
    @RequestMapping(value = "resetPassword.do")
    @ResponseBody
    public ServerResponse resetPassword(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return userService.resetPassword(map);
    }

    //更新个人信息
    @RequestMapping(value = "updateInfo.do")
    @ResponseBody
    public ServerResponse updateInfo(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return userService.updateInfo(map,request.getSession());
    }

}






