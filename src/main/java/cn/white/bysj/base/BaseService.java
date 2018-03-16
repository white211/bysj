package cn.white.bysj.base;

import cn.white.bysj.commons.ServerResponse;

/**
 * Create by @author white
 *
 * @date 2018-03-16 15:21
 */
public interface BaseService {

    ServerResponse ValidateToken(String token);

}
