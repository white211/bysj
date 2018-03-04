package cn.white.bysj.user;

import cn.white.bysj.commons.Constant;
import cn.white.bysj.commons.MyException;
import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.utils.*;
import cn.white.bysj.utils.redis.RedisService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.connection.ConnectionUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import sun.rmi.transport.ObjectTable;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/**
 * Create by @author white
 *
 * @date 2017-12-28 16:03
 */
@Service
public class UserService {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisService redisService;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Executor executor;

    //登录
    public ServerResponse<User> login(HttpSession session, Map<String, Object> map) {

        List<String> list = Arrays.asList("account", "password");
//         List<String> list= new ArrayList<>();
//         list.add("account");
//         list.add("password");
        if (!CollectionUtils.isEmpty(ValidatorUtil.validator(map, list))) {
            return ServerResponse.createByErrorMessage("缺少参数");
        }

        if (CollectionUtils.isEmpty(map)) {
            return ServerResponse.createByErrorMessage("没有带数据");
        }

        if (StringUtils.isEmpty(map.get("account").toString())) {
            return ServerResponse.createByErrorMessage("输入账户不能为空");
        }

        if (StringUtils.isEmpty(map.get("password").toString())) {
            return ServerResponse.createByErrorMessage("输入密码不能为空");
        }

        User user = userDao.selectUserByAccount(map.get("account").toString());
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户不存在");
        } else {
            if (user.getCn_user_password().equals(map.get("password").toString())) {
                //登陆成功保存到session
//                HttpSession session = request.getSession();
                session.setAttribute(Constant.CURRENT_USER_ACCOUNT, map.get("account").toString());
                session.setAttribute(Constant.CURRENT_USER_ID, user.getCn_user_id().toString());
                session.setAttribute(Constant.CURRENT_USER, user);
                System.out.println("获取session用户:" + session.getAttribute(Constant.CURRENT_USER_ACCOUNT));
                System.out.println("获取session用户id:" + session.getAttribute(Constant.CURRENT_USER_ID));
                user.setCn_user_password(org.apache.commons.lang3.StringUtils.EMPTY);
                return ServerResponse.createBySuccess("登陆成功", user);
            } else {
                return ServerResponse.createByErrorMessage("登陆密码错误");
            }
        }
    }

    //退出登录
    public ServerResponse<User> logout(Map<String, Object> map, HttpSession session) {
        session.removeAttribute("cn_user_id");
        session.removeAttribute("account");
//        System.out.println(session.getAttribute("account"));
        System.out.println("注销成功");
        return ServerResponse.createBySuccessMessags("注销成功");
    }

    //发送邮箱验证
    @Async("myExecutor")
    public Future<ServerResponse<User>> register(Map<String, Object> map) {

        if (map.get("email").toString() == null || map.get("email").toString() == "") {
            return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("输入邮箱不能为空"));
        }
        if (map.get("telephone").toString() == null || map.get("telephone").toString() == "") {
            return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("输入手机号不能为空"));
        }
        if (map.get("password").toString() == null || map.get("password").toString() == "") {
            return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("输入密码不能为空"));
        }
        if (map.get("checkNum").toString() == null || map.get("checkNum").toString() == "") {
            return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("输入验证码不能为空"));
        }

        //判断邮箱的正确性
        if (map.get("email").toString() != null && map.get("email").toString() != "") {
//            String[] email = map.get("email").toString().split("@");
            if (userDao.selectUserByEmail(map.get("email").toString()) > 0) {
                return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("邮箱已经被注册"));
            }
            if (CheckEmailUtil.checkEmail(map.get("email").toString())) {
                return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("邮箱格式错误"));
            }
        }
        //判断密码的正确性
        if (map.get("password").toString() != null && map.get("password").toString() != "") {
            if (map.get("password").toString().length() < 6) {
                return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("密码长度不能小于6"));
            }
        }

        //判断验证码
        if (map.get("checkNum").toString() == null || map.get("checkNum").toString() == "") {
            return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("验证码不能为空"));
        }

        User user = new User();
        user.setCn_user_actived_code(UUIDutils.getUUID());
        user.setCn_user_actived("0");
        user.setCn_user_email(map.get("email").toString());
        System.out.println("service层发送邮件");
        try {
            if (redisService.get(map.get("telephone").toString()).equals(map.get("checkNum").toString())) {
                //发送邮件
                System.out.println(redisService.get(map.get("telephone").toString()));
                user.setCn_user_password(map.get("password").toString());
                user.setCn_user_telephone(map.get("telephone").toString());
                executor.execute(new EmailUtil(user.getCn_user_actived_code(), user.getCn_user_email(), javaMailSender, 1));
                userDao.save(user);
            } else {
                System.out.println(redisService.get(map.get("telephone").toString()));
                System.out.println("验证码错误");
                return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("验证码输入错误，请重新输入"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("邮件发送出现异常"));
        }
        System.out.println("注册成功");
        user.setCn_user_password(org.apache.commons.lang3.StringUtils.EMPTY);
        return new AsyncResult<ServerResponse<User>>(ServerResponse.createBySuccess("注册成功,请到邮箱激活", user));
    }

    //根据code选择用户
    public User selectUserByCode(String code) {
        return userDao.selectUserByCode(code);
    }

    //更新激活后的用户状态
    public ServerResponse updateUserState(Map<String, Object> map) {
        User user = selectUserByCode(map.get("code").toString());
        if (user != null) {
            userDao.updateUserState(map.get("code").toString());
            return ServerResponse.createBySuccessMessags("激活成功");
        } else {
            return ServerResponse.createByErrorMessage("激活码错误，请重新注册");
        }
    }

    //发送手机验证码
    public ServerResponse sendCheckNum(Map<String, Object> map) {
        HashMap<String, String> re = null;
        try {
            if (!map.containsKey("telephone")) {
                throw new MyException(1, "传入参数名错误，必须为telephone");
            }
            re = SmsUtil.getCode(map.get("telephone").toString());
            System.out.println(re);
            if (re.containsKey("code")) {
                redisService.set(map.get("telephone").toString(), re.get("code"));
                System.out.println(redisService.get(map.get("telephone").toString()));
                return ServerResponse.createBySuccess(re.get("respDesc").toString(), re.get("code").toString());
            }
        } catch (MyException e) {
            return ServerResponse.createByErrorCodeMessage(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            return ServerResponse.createByErrorCodeMessage(-1, "服务器出现异常");
        }
        return ServerResponse.createByErrorMessage(re.get("respDesc").toString());
    }

    //判断邮箱是否存在
    public ServerResponse<User> checkEmailIsExist(Map<String, Object> map) {
        if (StringUtils.isEmpty(map.get("email").toString())) {
            return ServerResponse.createByErrorMessage("邮箱不能为空");
        } else {
            if (CheckEmailUtil.checkEmail(map.get("email").toString())) {
                return ServerResponse.createByErrorMessage("邮箱格式错误");
            }
            if (userDao.selectUserByEmail(map.get("email").toString()) > 0) {
                User user = userDao.selectUserByAccount(map.get("email").toString());
                user.setCn_user_password(org.apache.commons.lang3.StringUtils.EMPTY);
                return ServerResponse.createBySuccess("存在该注册邮箱", user);
            }
        }
        return null;
    }

    //验证手机验证码
    public ServerResponse checkTelephoneCheckNum(Map<String, Object> map) {
        if (StringUtils.isEmpty(map.get("telephone").toString())) {
            return ServerResponse.createByErrorMessage("手机号不能为空");
        }
        if (StringUtils.isEmpty(map.get("checkNum").toString())) {
            return ServerResponse.createByErrorMessage("请输入验证码");
        }
        if (redisService.get(map.get("telephone").toString()).equals(map.get("checkNum").toString())) {
            return ServerResponse.createBySuccessMessags("验证成功");
        } else {
            return ServerResponse.createByErrorMessage("验证码错误，请重新输入");
        }
    }

    //未登陆状态下-忘记密码
    public ServerResponse<User> forgetResetPassword(Map<String, Object> map) {
        List<String> list = Arrays.asList("email", "password");
        User user = null;
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数,参数包括email，password");
        }
        if (StringUtils.isEmpty(map.get("password").toString())) {
            return ServerResponse.createByErrorMessage("输入密码不能为空");
        } else if (StringUtils.isEmpty(map.get("email").toString())) {
            return ServerResponse.createByErrorMessage("修改账号不能为空");
        } else {
            try {
                if (userDao.findPasswordByCn_user_email(map.get("email").toString()).equals(map.get("password").toString())) {
                    return ServerResponse.createByErrorMessage("新密码不能与旧密码重复");
                } else {
                    userDao.forgetResetPassword(map.get("password").toString(), map.get("email").toString());
                    return ServerResponse.createBySuccessMessags("修改密码成功");
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                return ServerResponse.createByErrorMessage("修改密码出错");
            }
        }

    }

    //登陆状态下
    public ServerResponse<User> resetPassword(Map<String, Object> map) {
        List<String> list = Arrays.asList("email", "oldPassword", "newPassword");
//        User user = null;
        if (ValidatorUtil.validator(map, list).size() > 0) {

            System.out.println(ValidatorUtil.validator(map, list));

            return ServerResponse.createByErrorMessage("缺少参数，应包括email，oldPassword，newPassword");
        }
        if (StringUtils.isEmpty(map.get("email").toString())) {
            return ServerResponse.createByErrorMessage("邮箱不能为空");
        } else if (StringUtils.isEmpty(map.get("oldPassword"))) {
            return ServerResponse.createByErrorMessage("旧密码不能为空");
        } else if (StringUtils.isEmpty(map.get("newPassword"))) {
            return ServerResponse.createByErrorMessage("新密码不能为空");
        } else {
            try {
                if (map.get("oldPassword").toString().equals(map.get("newPassword").toString())) {
                    return ServerResponse.createByErrorMessage("新密码不能与旧密码相同");
                } else {
                    userDao.forgetResetPassword(map.get("newPassword").toString(), map.get("email").toString());
                    return ServerResponse.createBySuccessMessags("修改密码成功");
                }
            } catch (Exception e) {
                logger.error("更新密码出错");
                e.printStackTrace();
                return ServerResponse.createByErrorMessage("更新密码出错");
            }
        }
    }

    //更新个人信息
    public ServerResponse<User> updateInfo(Map<String, Object> map, HttpSession session) {
        User user = null;
        if (StringUtils.isEmpty(session.getAttribute(Constant.CURRENT_USER_ID))) {
            return ServerResponse.createByErrorMessage("未登录状态下不能进行修改");
        } else {
            user.setCn_user_id(Integer.parseInt(Constant.CURRENT_USER_ID));
            user = userDao.save(user);
            return ServerResponse.createBySuccess("修改个人信息成功", user);
        }
    }

    //更新个人头像
    public ServerResponse<User> updateIcon(Map<String, Object> map, HttpSession session) {
        return null;
    }

}









