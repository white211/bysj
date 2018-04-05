package cn.white.bysj.admin.service.impl;

import cn.white.bysj.admin.dao.IHomeDao;
import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.admin.entity.Home;
import cn.white.bysj.admin.service.IHomeServcie;
import cn.white.bysj.admin.service.support.IBaseService;
import cn.white.bysj.admin.service.support.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by @author white
 *
 * @date 2018-04-05 20:56
 */
@Service
public class IHomeServiceImpl extends BaseServiceImpl<Home,Integer>  implements IHomeServcie{

    @Autowired
    private IHomeDao iHomeDao;

    @Override
    public IBaseDao<Home, Integer> getBaseDao() {
        return this.iHomeDao;
    }


    @Override
    public void saveOrUpdate(Home home) {

    }


    @Override
    public void updateType(int id, String type) {

    }
}
