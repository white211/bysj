package cn.white.bysj.base;

import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.utils.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-03-16 15:21
 */
@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    private RedisService redisService;

    //验证用户token
    public ServerResponse ValidateToken(String token){
        if(StringUtils.isBlank(redisService.get(token).toString())){
           return ServerResponse.createByErrorMessage("请重新登陆，用户无效或登陆过期");
        }else{
           redisService.expire(token,10);
           return ServerResponse.createBySuccessMessags("验证成功并更新时间");
        }
    }


}
