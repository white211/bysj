package cn.white.bysj.admin.service;

import cn.white.bysj.admin.service.support.IBaseService;
import cn.white.bysj.admin.vo.FeedBackVo;
import cn.white.bysj.feedback.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Create by @author white
 *
 * @date 2018-04-05 11:24
 */
public interface IFeedBackService extends IBaseService<Feedback,Integer> {

    Page<FeedBackVo> findFeedBack(Pageable pageable);

    FeedBackVo findOne(int feedbackId);

    void UpdateAndSendEmail(FeedBackVo feedBackVo);

}
