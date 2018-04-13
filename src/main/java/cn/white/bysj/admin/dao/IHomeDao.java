package cn.white.bysj.admin.dao;

import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.admin.entity.Home;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Create by @author white
 *
 * @date 2018-04-05 20:55
 */
public interface IHomeDao extends IBaseDao<Home,Integer> {

    @Query(value = "select count(*) from tb_home where cn_home_type = ?2 and cn_home_id != ?1",nativeQuery = true)
     int findUsing(int id,int type);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update tb_home set cn_home_type = ?2 where  cn_home_id = ?1",nativeQuery = true)
    void updateHome(int id,String type);



}
