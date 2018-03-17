package cn.white.bysj.utils.interceptor;

import cn.white.bysj.utils.oauth.OAuthSessionManager;
import cn.white.bysj.utils.redis.RedisService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create by @author white
 *
 * @date 2018-03-16 23:00
 */
public class ClearTokenInteceptor extends HandlerInterceptorAdapter {

    private static final String SESSION_TIME_OUT_K = "SESSION_TIME_OUT";
    private static final String SESSION_TIME_OUT_V = "timeout";

    @Autowired
    private RedisService redisService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String token = request.getHeader(OAuthSessionManager.OAUTH_TOKEN);

        if(null != token){
            Session s = redisService.get(token, Session.class);

            if(null == s || null == s.getId()){
                response.setHeader(SESSION_TIME_OUT_K, SESSION_TIME_OUT_V);
            }
        }

        return super.preHandle(request, response, handler);
    }

}
