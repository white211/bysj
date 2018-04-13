package cn.white.bysj.home;

import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.admin.entity.Home;
import cn.white.bysj.admin.service.support.impl.BaseServiceImpl;
import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.feedback.FeedbackServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by @author white
 *
 * @date 2018-04-09 14:08
 */
@Service
public class HomeServiceImpl extends BaseServiceImpl<Home,Integer> implements HomeService{

    @Autowired
    private HomeDao homeDao;

    private static Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);


    @Override
    public IBaseDao<Home, Integer> getBaseDao() {
        return this.homeDao;
    }

    @Override
    public ServerResponse findHomeList() {
        try {
            List<Home> homeList = homeDao.findHomeList();
            return  ServerResponse.createBySuccess("首页数据",homeList);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查找失败");
            return ServerResponse.createByErrorMessage("查找失败");
        }

    }
}
