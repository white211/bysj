package cn.white.bysj.user;

import cn.white.bysj.commons.Constant;
import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.utils.ComponentHelper;
import cn.white.bysj.utils.Cors;
import cn.white.bysj.utils.QiNiu.QiniuService;
import cn.white.bysj.utils.UUIDutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Create by @author white
 *
 * @date 2017-12-28 16:04
 */
//@CrossOrigin(origins = "http://localhost:8082", maxAge = 3600)
@Controller
@RequestMapping(value = "user/")
public class UserController extends Cors  {
    @Autowired
    private UserService userService;

    @Autowired
    private QiniuService qiniuService;

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
    public ServerResponse<User> logout( HttpServletRequest request) {
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

    //验证旧密码
    @RequestMapping(value = "checkOldPassword.do")
    @ResponseBody
    public ServerResponse checkOldPassword(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return userService.checkOldPassword(map);
    }

    //通过id查找个人用户
    @RequestMapping(value = "findUserById.do")
    @ResponseBody
    public  ServerResponse findUserById(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return userService.findUserById(map);
    }

    //通过id更新个人头像
    @RequestMapping(value = "updataAvatar.do")
    @ResponseBody
    public  ServerResponse updataAvatar(@RequestParam("image") MultipartFile file,@RequestParam("userId") int userId){
        if (file.isEmpty()) {
            return ServerResponse.createByErrorMessage("文件不能为空");
        } else {
            try {
                // 包含原始文件名的字符串
                String fi = file.getOriginalFilename();
                // 提取文件拓展名
                String fileNameExtension = fi.substring(fi.indexOf("."), fi.length());
                // 生成云端的真实文件名
                String remoteFileName = UUIDutils.getUUID().toString() + fileNameExtension;
                qiniuService.upload(file.getBytes(), remoteFileName);
                String url = Constant.QINIU_DOMAIN_NAME+remoteFileName;
                userService.updateAvatar(userId,url);
                return ServerResponse.createBySuccess("上传成功",url);
            } catch (Exception e) {
                e.printStackTrace();
                return ServerResponse.createByErrorMessage("上传出错");
            }
        }
    }

    //通过城市查找天气
    @RequestMapping(value = "getWeather.do")
    @ResponseBody
    public ServerResponse<String> getWeather(HttpServletRequest request){
       Map<String,Object> map = ComponentHelper.requestToMap(request);
       return userService.weatherJson(map);
    }

    //获取当前所在城市
    @RequestMapping(value = "getCity.do")
    @ResponseBody
    public ServerResponse getCity(HttpServletRequest request) {
        return userService.getCity(request);
    }



}






