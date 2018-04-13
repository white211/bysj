package cn.white.bysj.admin.service.impl;

import cn.white.bysj.admin.dao.IHomeDao;
import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.admin.entity.Home;
import cn.white.bysj.admin.service.IHomeServcie;
import cn.white.bysj.admin.service.support.IBaseService;
import cn.white.bysj.admin.service.support.impl.BaseServiceImpl;
import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.user.User;
import groovy.util.logging.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;
import java.util.Date;

/**
 * Create by @author white
 *
 * @date 2018-04-05 20:56
 */
@Service
@Slf4j
public class IHomeServiceImpl extends BaseServiceImpl<Home, Integer> implements IHomeServcie {

    @Autowired
    private IHomeDao iHomeDao;

    @Override
    public IBaseDao<Home, Integer> getBaseDao() {
        return this.iHomeDao;
    }

    private static Logger logger = LoggerFactory.getLogger(IHomeServiceImpl.class);

    @Override
    public void saveOrUpdate(Home home) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (home.getCn_home_id() != null) {
            Home dbhome = find(home.getCn_home_id());
            dbhome.setCn_first_bpic(home.getCn_first_bpic());
            dbhome.setCn_first_spic(home.getCn_first_spic());
            dbhome.setCn_first_title(home.getCn_first_title());
            dbhome.setCn_first_desc(home.getCn_first_desc());
            dbhome.setCn_second_bpic(home.getCn_second_bpic());
            dbhome.setCn_second_spic(home.getCn_second_spic());
            dbhome.setCn_second_desc(home.getCn_second_desc());
            dbhome.setCn_second_title(home.getCn_second_title());
            dbhome.setCn_third_bpic(home.getCn_third_bpic());
            dbhome.setCn_third_spic(home.getCn_third_spic());
            dbhome.setCn_third_title(home.getCn_third_title());
            dbhome.setCn_third_desc(home.getCn_third_desc());
            update(dbhome);
        } else {
            home.setCnHomeCreateTime(new Date());
            home.setCnHomeUpdateTime(new Date());
            home.setCn_user_id(user.getCn_user_id());
            home.setCn_home_type("1");
            save(home);
        }
    }


    @Override
    public ServerResponse updateType(int id, String type) {
        try {
            if (Integer.parseInt(type) == 1) {
                int count = iHomeDao.findUsing(id, 0);
                if (count > 0) {
                    iHomeDao.updateHome(id,type);
                    logger.info("更新成功");
                    return ServerResponse.createBySuccessMessags("更新成功");
                } else {
                    logger.error("没有信息在展示，请使用其他信息");
                    return ServerResponse.createByErrorCodeMessage(405, "没有信息在展示，请先使用其他信息");
            }
            } else {
                iHomeDao.updateHome(id,type);
                logger.info("更新成功");
                return ServerResponse.createBySuccessMessags("更新成功");
            }

        } catch (Exception e) {
            logger.error("服务出现异常");
            return ServerResponse.createByErrorMessage("服务出现异常");
        }
    }
}
