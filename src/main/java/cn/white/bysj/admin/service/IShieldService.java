package cn.white.bysj.admin.service;

import cn.white.bysj.admin.entity.Shield;
import cn.white.bysj.admin.service.support.IBaseService;
import cn.white.bysj.admin.vo.ShieldVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Create by @author white
 *
 * @date 2018-04-01 22:42
 */
public interface IShieldService extends IBaseService<Shield,Integer> {

   Page<ShieldVo> findShield(Pageable pageable);

   void saveOrUpdate(Shield shield);

   ShieldVo findOne(int shield_id);
}
