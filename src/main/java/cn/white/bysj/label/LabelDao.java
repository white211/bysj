package cn.white.bysj.label;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create by @author white
 *
 * @date 2018-03-02 21:42
 */
public interface LabelDao extends JpaRepository<Label,Integer> {

    @Query(value = "select * from label where cn_user_id=?1 ORDER BY cn_label_last_update_time DESC ",nativeQuery = true)
    List<Label> findLabelByUserId(int userId);

    @Query(value = "select count(*) from label where cn_label_name =?1 AND cn_user_id=?2",nativeQuery = true)
    int findLabelByName(String labelName,int userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from label where cn_user_id =?1 and cn_label_id =?2",nativeQuery = true)
    void deleteLabelByUserIdAndLabelId(int userId,int labelId);

    @Query(value = "select * from label where cn_user_id = ?1 and cn_label_name like %?2%",nativeQuery = true)
    List<Label> findLabelByName(int userId,String searchText);

    @Query(value = "select count(*) from label where cn_user_id =?1 and cn_label_name=?2 and cn_label_id !=?3",nativeQuery = true)
    int findLabelByNameAndUserId(int userId,String newName,int labelId );

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update label SET cn_label_name = ?2 WHERE cn_user_id = ?1 AND cn_label_id=?3",nativeQuery = true)
    void updateLabelName(int userId,String newName,int labelId);
}

