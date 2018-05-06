package cn.white.bysj.user;


import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.utils.*;

import cn.white.bysj.utils.GetCityUtil;
import cn.white.bysj.utils.redis.RedisService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import groovy.util.logging.Slf4j;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.security.NoSuchAlgorithmException;
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
@Slf4j
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

    /**
     * TODO: 用户登陆
     *
     * @return cn.white.bysj.commons.ServerResponse<cn.white.bysj.user.User>
     * @throws
     * @author white
     * @date 2018-03-19 13:24
     * @params "account", "password"
     */
    public ServerResponse<User> login(Map<String, Object> map) {

        List<String> list = Arrays.asList("account", "password");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数,应该包括account，password");
        }
        if (StringUtils.isBlank(map.get("account").toString())) {
            return ServerResponse.createByErrorMessage("输入账户不能为空");
        }
        if (StringUtils.isBlank(map.get("password").toString())) {
            return ServerResponse.createByErrorMessage("输入密码不能为空");
        }
        try {
            User user = userDao.selectUserByAccount(map.get("account").toString());
            if (user == null) {
                return ServerResponse.createByErrorMessage("用户不存在或尚未激活");
            } else {
                if (user.getCn_user_password().equals(MD5.md5(map.get("password").toString()))) {
                    if (user.getCn_user_locked().equals(1)) {
                        logger.error("登陆失败，该用户已经被锁定");
                        return ServerResponse.createByErrorMessage("登陆失败，该用户已被锁定");
                    } else {
                        String token = UUID.randomUUID().toString();
                        user.setCn_user_token(token);
                        logger.info("登陆成功生成的token--------" + token);
                        redisService.set(token, user, 60*60*2);
                        user.setCn_user_password(org.apache.commons.lang3.StringUtils.EMPTY);
                        if (!user.getCnNoteReadPassword().equals(MD5.md5("111111"))) {
                            user.setCnNoteReadPassword(org.apache.commons.lang3.StringUtils.EMPTY);
                        }
                        logger.info("登陆成功");
                        return ServerResponse.createBySuccess("登陆成功", user);
                    }
                } else {
                    logger.error("登陆密码错误");
                    return ServerResponse.createByErrorMessage("登陆密码错误");
                }
            }
        } catch (Exception e) {
            logger.error("服务器出错");
            return ServerResponse.createByErrorMessage("服务器异常");
        }

    }

    /**
     * TODO: 用户退出登陆
     *
     * @param
     * @return
     * @throws
     * @author white
     * @date 2018-03-19 13:25
     */
    public ServerResponse<User> logout(Map<String, Object> map, HttpSession session) {
        System.out.println("注销成功");
        return ServerResponse.createBySuccessMessags("注销成功");
    }

    /**
     * TODO: 注册之后 发邮箱功能
     *
     * @param "email","password","telephone","checkNum"
     * @return
     * @throws
     * @author white
     * @date 2018-03-19 13:26
     */
    @Async("myExecutor")
    public Future<ServerResponse<User>> register(Map<String, Object> map) {
        //判断前台传递参数是否正确
        List<String> list = Arrays.asList("email", "password", "telephone", "checkNum");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("缺少参数，必须包括email，password" +
                    "telephone,checKNum"));
        }
        //判断邮箱的正确性
        if (!StringUtils.isBlank(map.get("email").toString())) {
            if (userDao.selectUserByEmail(map.get("email").toString()) > 0) {
                return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("邮箱已经被注册"));
            }
            if (CheckEmailUtil.checkEmail(map.get("email").toString())) {
                return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("邮箱格式错误"));
            }
        } else {
            return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("邮箱不能为空"));
        }
        //判断密码的正确性
        if (!StringUtils.isBlank(map.get("password").toString())) {
            if (map.get("password").toString().length() < 6) {
                return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("密码长度不能小于6"));
            }
        } else {
            return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("密码不能为空"));
        }
        if (StringUtils.isBlank(map.get("telephone").toString())) {
            return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("电话号码不能为空"));
        } else if (map.get("telephone").toString().length() != 11) {
            return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("电话号码长度必须为11位"));
        }
        //判断验证码
        if (StringUtils.isBlank(map.get("checkNum").toString())) {
            return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("验证码不能为空"));
        }
        User user = new User();
        user.setCn_user_actived_code(UUIDutils.getUUID());
        user.setCn_user_actived("0");
        user.setCn_user_email(map.get("email").toString());
        try {
            logger.info("redis开始获取验证码");
            if (redisService.containKey(map.get("telephone").toString())) {
                if (!redisService.get(map.get("telephone").toString()).equals(map.get("checkNum").toString())) {
                    return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("验证码错误,请重新输入"));
                }
                //发送邮件
                user.setCn_user_password(MD5.md5(map.get("password").toString()));
                user.setCnNoteReadPassword(MD5.md5("111111"));
                user.setCn_user_telephone(map.get("telephone").toString());
                user.setCn_user_locked(0);
                user.setCnUserUpdateTime(new Date());
                user.setCnUserCreateTime(new Date());
                String content = "";
                executor.execute(new EmailUtil(user.getCn_user_actived_code(), user.getCn_user_email(), javaMailSender, content, 1));
                userDao.save(user);
            } else {
                logger.error("验证码过期");
                return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("验证码过期，请重新获取"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("注册失败--服务异常");
            return new AsyncResult<ServerResponse<User>>(ServerResponse.createByErrorMessage("注册失败--服务异常"));
        }
        logger.info("注册成功");
        user.setCn_user_password(org.apache.commons.lang3.StringUtils.EMPTY);
        return new AsyncResult<ServerResponse<User>>(ServerResponse.createBySuccess("注册成功,请到邮箱激活", user));
    }

    /**
     * TODO: 通过用户注册生成的code查找用户
     *
     * @param code
     * @return
     * @throws
     * @author white
     * @date 2018-03-19 13:26
     */
    public User selectUserByCode(String code) {
        return userDao.selectUserByCode(code);
    }

    /**
     * TODO: 用户激活更新userState
     *
     * @param "code"
     * @return
     * @throws
     * @author white
     * @date 2018-03-19 13:28
     */
    public ServerResponse updateUserState(Map<String, Object> map) {
        List<String> list = Arrays.asList("code");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，应该包括code");
        }
        if (StringUtils.isBlank(map.get("code").toString())) {
            return ServerResponse.createByErrorMessage("code不能为空");
        }
        try {
            User user = selectUserByCode(map.get("code").toString());
            if (user != null) {
                userDao.updateUserState(map.get("code").toString());
                return ServerResponse.createBySuccessMessags("激活成功,请前往登陆");
            } else {
                return ServerResponse.createByErrorMessage("激活码错误，请重新注册");
            }
        } catch (Exception e) {
            logger.error("服务出现异常");
            return ServerResponse.createByErrorMessage("系统出现异常");
        }
    }

    /**
     * TODO: 发送验证码
     *
     * @param "telephone"
     * @return
     * @throws
     * @author white
     * @date 2018-03-19 13:33
     */

    public ServerResponse sendCheckNum(Map<String, Object> map) {
        HashMap<String, String> re = null;
        List<String> list = Arrays.asList("telephone");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("参数名出错，必须为telephone");
        }
        if (StringUtils.isBlank(map.get("telephone").toString())) {
            return ServerResponse.createByErrorMessage("电话号码不能为空");
        } else {
            try {
                String random = SmsUtil.getSms();
                long timeout = 60;
                logger.info("redis保存验证码60秒");
                redisService.set(map.get("telephone").toString(), random, timeout);
                return ServerResponse.createBySuccessMessags("验证码为" + random + " " + "有效期为60s");
//                短信发送验证码
//                re = SmsUtil.getCode(map.get("telephone").toString(), random);
//                if (re.containsKey("code")) {
//                    return ServerResponse.createBySuccess(re.get("respDesc").toString(), re.get("code").toString());
//                } else {
//                    return ServerResponse.createByErrorMessage(re.get("respDesc").toString());
//                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                return ServerResponse.createByErrorCodeMessage(-1, "服务器出现异常");
            }
        }
    }

    /**
     * TODO: 判断注册邮箱是否存在
     *
     * @param "email"
     * @return
     * @throws
     * @author white
     * @date 2018-03-19 13:33
     */
    public ServerResponse<User> checkEmailIsExist(Map<String, Object> map) {
        List<String> list = Arrays.asList("email");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，应包括email");
        }

        if (StringUtils.isBlank(map.get("email").toString())) {
            return ServerResponse.createByErrorMessage("邮箱不能为空");
        } else {
            if (CheckEmailUtil.checkEmail(map.get("email").toString())) {
                return ServerResponse.createByErrorMessage("邮箱格式错误");
            } else {
                try {
                    if (userDao.selectUserByEmail(map.get("email").toString()) > 0) {
                        User user = userDao.selectUserByAccount(map.get("email").toString());
                        user.setCn_user_password(org.apache.commons.lang3.StringUtils.EMPTY);
                        return ServerResponse.createBySuccess("存在该注册邮箱", user);
                    } else {
                        return ServerResponse.createByErrorMessage("不存在该邮箱");
                    }
                } catch (Exception e) {
                    logger.error("服务出错--判读邮箱是否存在");
                    return ServerResponse.createByErrorMessage("服务出现异常");
                }
            }
        }
    }

    /**
     * TODO: 验证手机验证码
     *
     * @param "telephone" "checkNum"
     * @return
     * @throws
     * @author white
     * @date 2018-03-19 13:34
     */
    public ServerResponse checkTelephoneCheckNum(Map<String, Object> map) {
        List<String> list = Arrays.asList("telephone", "checkNum");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，应包括telephone,checkNum");
        }

        if (StringUtils.isBlank(map.get("telephone").toString())) {
            return ServerResponse.createByErrorMessage("手机号不能为空");
        }
        if (StringUtils.isBlank(map.get("checkNum").toString())) {
            return ServerResponse.createByErrorMessage("请输入验证码");
        }
        try {
            if (redisService.get(map.get("telephone").toString()).equals(map.get("checkNum").toString())) {
                return ServerResponse.createBySuccessMessags("验证成功");
            } else {
                return ServerResponse.createByErrorMessage("验证码错误，请重新输入");
            }
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("验证密码已经过期，请重新获取");
        }
    }

    /**
     * TODO: 未登陆状态下 更新密码
     *
     * @param "userId", "password"
     * @return
     * @throws
     * @author white
     * @date 2018-03-19 13:35
     */
    public ServerResponse<User> forgetResetPassword(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId", "password");
        User user = null;
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数,参数包括userId，password");
        }
        if (StringUtils.isEmpty(map.get("password").toString())) {
            return ServerResponse.createByErrorMessage("输入密码不能为空");
        } else if (StringUtils.isEmpty(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("修改账号不能为空");
        } else {
            try {
                User user1 = userDao.findOne(Integer.parseInt(map.get("userId").toString()));

                if (user1.getCn_user_password().equals(MD5.md5(map.get("password").toString()))) {
                    return ServerResponse.createByErrorMessage("新密码不能与旧密码重复");
                } else {
                    userDao.forgetResetPassword(MD5.md5(map.get("password").toString()), Integer.parseInt(map.get("userId").toString()));
                    return ServerResponse.createBySuccessMessags("修改密码成功");
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                return ServerResponse.createByErrorMessage("修改密码出错");
            }
        }

    }

    /**
     * TODO: 登陆状态下更新密码(登陆密码；阅读密码)
     *
     * @param "userId", "oldPassword", "newPassword","type"
     * @return
     * @throws
     * @author white
     * @date 2018-03-19 13:35
     */
    public ServerResponse<User> resetPassword(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId", "oldPassword", "newPassword", "type");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，应包括userId，oldPassword，newPassword,type");
        }
        if (StringUtils.isEmpty(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("邮箱不能为空");
        } else if (StringUtils.isBlank(map.get("oldPassword").toString())) {
            return ServerResponse.createByErrorMessage("旧密码不能为空");
        } else if (StringUtils.isBlank(map.get("newPassword").toString())) {
            return ServerResponse.createByErrorMessage("新密码不能为空");
        } else {
            try {
                if (map.get("oldPassword").toString().equals(map.get("newPassword").toString())) {
                    return ServerResponse.createByErrorMessage("新密码不能与旧密码相同");
                } else {
                    if (map.get("type").toString().equals("1")) {
                        userDao.forgetResetPassword(MD5.md5(map.get("newPassword").toString()), Integer.parseInt(map.get("userId").toString()));
                        return ServerResponse.createBySuccessMessags("修改密码成功");
                    } else {
                        userDao.updateReadPass(MD5.md5(map.get("newPassword").toString()), Integer.parseInt(map.get("userId").toString()));
                        User user = userDao.findOne(Integer.parseInt(map.get("userId").toString()));
                        user.setCnNoteReadPassword(org.apache.commons.lang3.StringUtils.EMPTY);
                        return ServerResponse.createBySuccess("修改密码成功", user);
                    }
                }
            } catch (Exception e) {
                logger.error("更新密码出错");
                e.printStackTrace();
                return ServerResponse.createByErrorMessage("更新密码出错");
            }
        }
    }

    /**
     * TODO: 更新个人信息
     *
     * @param
     * @return
     * @throws
     * @author white
     * @date 2018-03-19 13:36
     */
    public ServerResponse<User> updateInfo(Map<String, Object> map) {

        List<String> list = Arrays.asList("userId", "nickname", "name", "birthday", "sex", "address");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，必须包括userId,nickname,name,birthday,sex,address");
        } else if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("用户id不能为空");
        }
        try {
            int userId = Integer.parseInt(map.get("userId").toString());
            String nickName = StringUtils.isBlank(map.get("nickname").toString()) ? "" : map.get("nickname").toString();
            String name = StringUtils.isBlank(map.get("name").toString()) ? "" : map.get("name").toString();
            String birthday = StringUtils.isBlank(map.get("birthday").toString()) ? "" : map.get("birthday").toString();
            int sex = StringUtils.isBlank(map.get("name").toString()) ? 1 : Integer.parseInt(map.get("sex").toString());
            String address = StringUtils.isBlank(map.get("address").toString()) ? "" : map.get("address").toString();
            userDao.updateInfo(userId, name, nickName, birthday, sex, address);
            User user = userDao.findOne(userId);
            return ServerResponse.createBySuccess("保存成功", user);
        } catch (Exception e) {
            logger.error("保存失败");
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("保存用户信息失败");
        }

    }

    /**
     * TODO: 更新用户头像
     *
     * @param "userId","url"
     * @return
     * @throws
     * @author white
     * @date 2018-03-23 21:20
     */
    public ServerResponse<User> updateAvatar(int userId, String url) {

        if (StringUtils.isBlank(String.valueOf(userId))) {
            return ServerResponse.createByErrorMessage("用户id不能为空");
        } else if (StringUtils.isBlank(url)) {
            return ServerResponse.createByErrorMessage("头像链接不能为空");
        } else {
            try {
                userDao.updateUserAvatar(userId, url);
                return ServerResponse.createBySuccessMessags("更新成功");
            } catch (Exception e) {
                logger.error("更换失败");
                return ServerResponse.createByErrorMessage("更换失败");
            }
        }

    }

    /**
     * TODO: 对比输入旧密码是否正确
     *
     * @param "userId",'oldPassword','type'
     * @return
     * @throws
     * @author white
     * @date 2018-03-19 15:53
     */
    public ServerResponse checkOldPassword(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId", "oldPassword", "type");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，必须包括userId,oldPassword,type");
        }
        if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("邮箱不能为空");
        } else if (StringUtils.isBlank(map.get("oldPassword").toString())) {
            return ServerResponse.createByErrorMessage("旧密码不能为空");
        } else {
            User user = userDao.findOne(Integer.parseInt(map.get("userId").toString()));
            try {
                if (map.get("type").toString().equals("1")) {
                    String password = user.getCn_user_password();
                    if (!password.equals(MD5.md5(map.get("oldPassword").toString()))) {
                        return ServerResponse.createByErrorMessage("旧密码错误");
                    } else {
                        return ServerResponse.createBySuccessMessags("密码正确");
                    }
                } else {
                    String password = user.getCnNoteReadPassword();
                    if (!password.equals(MD5.md5(map.get("oldPassword").toString()))) {
                        return ServerResponse.createByErrorMessage("旧密码错误");
                    } else {
                        return ServerResponse.createBySuccessMessags("密码正确");
                    }
                }
            } catch (Exception e) {
                logger.error("验证旧密码----服务出现异常");
                return ServerResponse.createByErrorMessage("验证旧密码-----服务出现异常");
            }
        }
    }

    /**
     * TODO: 通过id查找用户
     *
     * @param "userId"
     * @return
     * @throws
     * @author white
     * @date 2018-03-20 18:41
     */
    public ServerResponse<User> findUserById(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，必须包括userId");
        }
        if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("用户id不能为空");
        } else {
            try {
                int userId = Integer.parseInt(map.get("userId").toString());
                User user = userDao.findOne(userId);
                return ServerResponse.createBySuccess("用户信息", user);
            } catch (Exception e) {
                logger.error("服务出现异常");
                return ServerResponse.createByErrorMessage("服务出现异常");
            }
        }
    }

    /**
     * TODO: 通过用户访问ip获取当前所在城市
     *
     * @return
     * @throws
     * @author white
     * @date 2018-03-27 15:35
     */
    public ServerResponse<String> getCity(HttpServletRequest request) {
        try {
            String city = GetCityUtil.getCity(request);
            return ServerResponse.createBySuccess("获取城市成功", city);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取城市失败");
            return ServerResponse.createByErrorMessage("获取城市失败");
        }
    }


    /**
     * TODO: 通过城市到心知天气上面获取天气数据
     *
     * @return
     * @throws
     * @author white
     * @date 2018-03-25 10:20
     */
    public ServerResponse weatherJson(HttpServletRequest request) {

        String city;
        if (this.getCity(request).getStatus() == 0) {
            city = this.getCity(request).getData();
            logger.info(city);
        } else {
            return this.getCity(request);
        }
//        List<String> list = Arrays.asList("city");
//        if (ValidatorUtil.validator(map, list).size() > 0) {
//            return ServerResponse.createByErrorMessage("缺少参数city");
//        }
//        if (StringUtils.isBlank(map.get("city").toString())) {
//            return ServerResponse.createByErrorMessage("城市不能为空");
//        } else {
//            String city = map.get("city").toString();
        try {
            String url = WeatherUtil.generateGetDiaryWeatherURL(city, "", "c", "1", "1");
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpClient.execute(httpGet, responseHandler);
            JSONObject jsonObject = JSONObject.parseObject(responseBody);
            return ServerResponse.createBySuccess("获取天气成功", jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取天气数据失败");
            return ServerResponse.createByErrorMessage("获取天气数据失败------查找失败");
        }
    }


}









