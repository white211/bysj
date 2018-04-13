package cn.white.bysj.home;

import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.admin.entity.Home;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Create by @author white
 *
 * @date 2018-04-09 14:07
 */
public interface HomeDao extends IBaseDao<Home,Integer>{

    @Query(value = "SELECT * from tb_home where cn_home_type =0 ORDER BY cn_home_update_time DESC ",nativeQuery = true)
    List<Home> findHomeList();

}
