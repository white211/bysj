package cn.white.bysj.admin.service.impl;

import cn.white.bysj.admin.dao.IShieldDao;
import cn.white.bysj.admin.dao.IUserDao;
import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.admin.entity.Shield;
import cn.white.bysj.admin.service.IShieldService;
import cn.white.bysj.admin.service.support.impl.BaseServiceImpl;
import cn.white.bysj.user.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import cn.white.bysj.admin.vo.ShieldVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by @author white
 *
 * @date 2018-04-01 22:43
 */
@Service
public class IShieldServiceImpl extends BaseServiceImpl<Shield,Integer> implements IShieldService {

    @Autowired
    private IShieldDao iShieldDao;

    @Autowired
    private IUserDao iUserDao;

    /**
     * TODO: 分页查询列表
     * @author white
     * @date 2018-04-08 10:44
       @param pageable
     * @return
     * @throws
     */
    @Override
    public Page<ShieldVo> findShield(Pageable pageable) {
        List<ShieldVo> sheildVolist = new ArrayList<>();
        Page<Shield> sheilds = iShieldDao.findAll(pageable);
        for (Shield shield:sheilds){
            ShieldVo sheildVo = new ShieldVo();
            sheildVo.setCn_shield_id(shield.getCn_shield_id());
            sheildVo.setCn_shield_content(shield.getCn_shield_content());
            sheildVo.setCn_shield_createTime(shield.getCnShieldCreateTime());
            String email = iUserDao.findEmailById(shield.getCn_user_id());
            sheildVo.setCn_user_name(email);
            sheildVolist.add(sheildVo);
        }
        Page<ShieldVo> sheildVos = new PageImpl<ShieldVo>(sheildVolist,pageable,sheilds.getTotalElements());
        return sheildVos;
    }

    /**
     * TODO: 添加或者修改
     * @author white
     * @date 2018-04-08 10:45
       @param shield
     * @return
     * @throws
     */
    @Override
    public void saveOrUpdate(Shield shield) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        if (shield.getCn_shield_id() != null){
            Shield  dbshield = find(shield.getCn_shield_id());
            dbshield.setCn_shield_content(shield.getCn_shield_content());
            dbshield.setCn_shield_id(shield.getCn_shield_id());
            dbshield.setCn_user_id(shield.getCn_user_id());
            update(dbshield);
        }else{
            shield.setCnShieldCreateTime(new Date());
            shield.setCn_user_id(user.getCn_user_id());
            save(shield);
        }
    }

    /**
     * TODO: 查询某一条记录
     * @author white
     * @date 2018-04-08 15:38
       @param shield_id
     * @return
     * @throws
     */
    @Override
    public ShieldVo findOne(int shield_id) {
        ShieldVo shieldVo = new ShieldVo();
        Shield shield = iShieldDao.findOne(shield_id);
        shieldVo.setCn_shield_id(shield_id);
        shieldVo.setCn_shield_content(shield.getCn_shield_content());
        shieldVo.setCn_user_name(iUserDao.findEmailById(shield.getCn_user_id()));
        shieldVo.setCn_shield_createTime(shield.getCnShieldCreateTime());
        shieldVo.setCn_user_id(shield.getCn_user_id());
        return shieldVo;
    }

    /**
     * TODO: 继承父类用户获取Dao
     * @author white
     * @date 2018-04-08 10:45

     * @return
     * @throws
     */
    @Override
    public IBaseDao<Shield, Integer> getBaseDao() {
        return this.iShieldDao;
    }



}
