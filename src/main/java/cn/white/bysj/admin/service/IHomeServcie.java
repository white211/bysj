package cn.white.bysj.admin.service;

import cn.white.bysj.admin.entity.Home;
import cn.white.bysj.admin.service.support.IBaseService;

/**
 * Create by @author white
 *
 * @date 2018-04-05 20:56
 */
public interface IHomeServcie extends IBaseService<Home,Integer> {

    void saveOrUpdate(Home home);

    void updateType(int id,String type);
}
