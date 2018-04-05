package cn.white.bysj.admin.service.impl;

import cn.white.bysj.admin.dao.IFeedBackDao;
import cn.white.bysj.admin.dao.IUserDao;
import cn.white.bysj.admin.dao.support.IBaseDao;
import cn.white.bysj.admin.service.IFeedBackService;
import cn.white.bysj.admin.service.support.impl.BaseServiceImpl;
import cn.white.bysj.admin.vo.FeedBackVo;
import cn.white.bysj.commons.MyException;
import cn.white.bysj.feedback.Feedback;
import cn.white.bysj.user.User;
import cn.white.bysj.utils.EmailUtil;
import cn.white.bysj.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Create by @author white
 *
 * @date 2018-04-05 11:24
 */
@Service
@lombok.extern.slf4j.Slf4j
public class IFeedBackServiceImpl extends BaseServiceImpl<Feedback, Integer> implements IFeedBackService {

    @Autowired
    private IFeedBackDao iFeedBackDao;

    @Autowired
    private IUserDao iUserDao;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Executor executor;

    @Override
    public IBaseDao<Feedback, Integer> getBaseDao() {
        return this.iFeedBackDao;
    }

    private static Logger logger = LoggerFactory.getLogger(IFeedBackService.class);

    @Override
    public Page<FeedBackVo> findFeedBack(Pageable pageable) {
        List<FeedBackVo> feedBackVos = new ArrayList<>();
        Page<Feedback> page = iFeedBackDao.findAll(pageable);
        for (Feedback feedback : page) {
            FeedBackVo feedBackVo = new FeedBackVo();
            feedBackVo.setCn_feedback_id(feedback.getCn_feedback_id());
            feedBackVo.setCn_feedback_content(feedback.getCn_feedback_content());
            feedBackVo.setCn_feedback_createTime(feedback.getCn_feedback_createTime());
            feedBackVo.setCn_feedback_isReturn(feedback.getCn_feedback_isReturn());
            feedBackVo.setCn_feedback_returnContent(feedback.getCn_feedback_returnContent());
            feedBackVo.setCn_feedback_type(feedback.getCn_feedback_type());
            User user = iUserDao.findOne(StringUtils.isBlank(feedback.getCn_user_id().toString()) ? 0 : feedback.getCn_user_id());
            feedBackVo.setCn_user_email(user.getCn_user_email());
            if (feedback.getCn_userReturn_id() == null) {
                User user1 = (User) SecurityUtils.getSubject().getPrincipal();
                feedBackVo.setCn_userReturn_name(user1.getCn_user_name());
                System.out.println(user1.getCn_user_name());
            } else {
                User user2 = iUserDao.findOne(feedback.getCn_user_id());
                feedBackVo.setCn_userReturn_name(user2.getCn_user_name());
            }
            feedBackVos.add(feedBackVo);
        }
        Page<FeedBackVo> feedBackVos1 = new PageImpl<FeedBackVo>(feedBackVos);
        return feedBackVos1;
    }

    @Override
    public FeedBackVo findOne(int feedbackId) {
        FeedBackVo feedBackVo = new FeedBackVo();
        try {
            Feedback feedback = iFeedBackDao.findOne(feedbackId);
            feedBackVo.setCn_feedback_id(feedback.getCn_feedback_id());
            feedBackVo.setCn_feedback_type(feedback.getCn_feedback_type());
            feedBackVo.setCn_feedback_content(feedback.getCn_feedback_content());
            feedBackVo.setCn_feedback_createTime(feedback.getCn_feedback_createTime());
            feedBackVo.setCn_feedback_isReturn(feedback.getCn_feedback_isReturn());
            User user = iUserDao.findOne(StringUtils.isBlank(feedback.getCn_user_id().toString()) ? 0 : feedback.getCn_user_id());
            feedBackVo.setCn_user_email(user.getCn_user_email());
            if (feedback.getCn_userReturn_id() == null) {
                User user1 = (User) SecurityUtils.getSubject().getPrincipal();
                feedBackVo.setCn_userReturn_name(user1.getCn_user_name());
                System.out.println(user1.getCn_user_name());
            } else {
                User user1 = iUserDao.findOne(feedback.getCn_userReturn_id());
                feedBackVo.setCn_userReturn_name(user1.getCn_user_name());
            }
            feedBackVo.setCn_feedback_returnContent(feedback.getCn_feedback_returnContent());
            return feedBackVo;
        } catch (Exception e) {
            logger.error("查询出错");
            return null;
        }

    }

    @Override
    @Async("myExecutor")
    public void UpdateAndSendEmail(FeedBackVo feedBackVo) {
        try {
            if (StringUtils.isBlank(feedBackVo.getCn_feedback_id().toString())) {
                throw new MyException(1, "传入的反馈id不能为空");
            } else if (StringUtils.isBlank(feedBackVo.getCn_user_email())) {
                throw new MyException(1, "传入的邮箱不能为空");
            } else {
                int id = feedBackVo.getCn_feedback_id();
                String email = feedBackVo.getCn_user_email();
                String content = feedBackVo.getCn_feedback_returnContent();
                User user1 = (User) SecurityUtils.getSubject().getPrincipal();
                int returnUser_id = user1.getCn_user_id();
                executor.execute(new EmailUtil("", email, javaMailSender, content, 2));
                iFeedBackDao.UpdateAndSendEmail(id, content, returnUser_id);
            }
        } catch (MyException e) {
            logger.error(e.getMessage());
        }
    }


}
