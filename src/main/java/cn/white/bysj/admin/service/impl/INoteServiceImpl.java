package cn.white.bysj.admin.service.impl;

import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.admin.service.INoteService;
import cn.white.bysj.admin.service.support.impl.BaseServiceImpl;
import cn.white.bysj.user.User;
import org.springframework.stereotype.Service;

/**
 * Create by @author white
 *
 * @date 2018-03-05 12:10
 */
@Service
public class INoteServiceImpl extends BaseServiceImpl<User, Integer> implements INoteService {

    @Override
    public IBaseDao<User, Integer> getBaseDao() {
        return null;
    }


}
