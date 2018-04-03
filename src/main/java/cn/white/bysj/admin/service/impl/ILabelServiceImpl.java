package cn.white.bysj.admin.service.impl;

import cn.white.bysj.admin.dao.ILabelDao;
import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.admin.service.ILabelService;
import cn.white.bysj.admin.service.support.impl.BaseServiceImpl;
import cn.white.bysj.label.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by @author white
 *
 * @date 2018-04-02 12:59
 */
@Service
public class ILabelServiceImpl extends BaseServiceImpl<Label,Integer> implements ILabelService {

    @Autowired
    private ILabelDao iLabelDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.iLabelDao;
    }


}
