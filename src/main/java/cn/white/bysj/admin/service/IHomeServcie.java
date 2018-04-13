package cn.white.bysj.admin.service;

import cn.white.bysj.admin.entity.Home;
import cn.white.bysj.admin.service.support.IBaseService;
import cn.white.bysj.commons.ServerResponse;

/**
 * Create by @author white
 *
 * @date 2018-04-05 20:56
 */
public interface IHomeServcie extends IBaseService<Home,Integer> {

    void saveOrUpdate(Home home);

    ServerResponse updateType(int id, String type);
}
