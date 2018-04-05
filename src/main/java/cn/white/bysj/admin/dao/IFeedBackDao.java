package cn.white.bysj.admin.dao;

import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.feedback.Feedback;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Create by @author white
 *
 * @date 2018-04-05 11:22
 */
public interface IFeedBackDao extends IBaseDao<Feedback,Integer> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update feedback set cn_feedback_is_return = 0,cn_feedback_return_content = ?2,cn_user_return_id = ?3 where cn_feedback_id=?1",nativeQuery = true)
    void UpdateAndSendEmail(int id,String content,int returnUser_id);
}
