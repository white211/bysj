package cn.white.bysj.home;

import cn.white.bysj.admin.entity.Home;
import cn.white.bysj.admin.service.support.IBaseService;
import cn.white.bysj.commons.ServerResponse;

/**
 * Create by @author white
 *
 * @date 2018-04-09 14:08
 */
public interface HomeService extends IBaseService<Home,Integer> {

    ServerResponse findHomeList();

}
