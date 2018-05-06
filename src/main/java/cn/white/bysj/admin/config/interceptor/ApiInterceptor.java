package cn.white.bysj.admin.config.interceptor;

import cn.white.bysj.user.UserService;
import cn.white.bysj.utils.redis.RedisService;
import groovy.util.logging.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/**
 * 拦截器主要用于前端请求api拦截
 * Create by @author white
 *
 * @date 2018-05-03 12:17
 */
@Component
@Slf4j
public class ApiInterceptor implements HandlerInterceptor {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private RedisService redisService;

    /**
     * controller方法之前调用
     *
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object o) throws Exception {

        if (RequestMethod.OPTIONS.toString().equals(request.getMethod())) {
            logger.info("前台api接口拦截器------跨域预请求直接放行" + request.getRequestURI());
            return true;
        }
        if (StringUtils.isBlank(request.getHeader("Authorization"))) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            JSONObject json = new JSONObject();
            json.put("data","");
            json.put("msg","没有权限访问 Forbidden");
            json.put("status",403);
            PrintWriter out = null;
            out = response.getWriter();
            out.append(json.toString());
//            response.sendRedirect(request.getContextPath() + "/error/noAuth.do");
            return false;
        }
        String token = request.getHeader("Authorization");
        logger.info("前台api接口拦截器------前台请求token" +" "+ token);
        if (redisService.containKey(token)) {
            logger.info("前台api接口拦截器------redis存在此token" +" "+ token);
            return true;
        } else {
            response.sendError(401,"token已经过期");
//            response.sendRedirect(request.getContextPath()+"/error/timeout.do");
            logger.info("前台api接口拦截器------redis上token已经过期");
            return false;
        }
    }

    /***
     * controller方法调用之后执行
     * @param request
     * @param response
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object o,
                           ModelAndView modelAndView) throws Exception {
    }

    /**
     * 渲染了真个视图之后调用
     *
     * @param request
     * @param response
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object o, Exception e) throws Exception {
    }


}
