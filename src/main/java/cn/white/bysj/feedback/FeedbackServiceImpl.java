package cn.white.bysj.feedback;

import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.utils.ValidatorUtil;
import groovy.util.logging.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-04-01 21:56
 */
@Service
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

    private static Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);


    @Override
    public ServerResponse<Feedback> newFeedBack(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId", "type", "content");
        if (ValidatorUtil.validator(map, list).size() > 0) {
            return ServerResponse.createByErrorMessage("缺少参数，必须包括userId,type,content");
        }
        if (StringUtils.isBlank(map.get("userId").toString())) {
            return ServerResponse.createByErrorMessage("用户id不能为空");
        } else if (StringUtils.isBlank(map.get("type").toString())) {
            return ServerResponse.createByErrorMessage("类型不能为空");
        } else if (StringUtils.isBlank(map.get("content").toString())) {
            return ServerResponse.createByErrorMessage("内容不能为空");
        } else {
            try {
                Feedback feedback = new Feedback();
                feedback.setCnFeedbackCreateTime(new Date());
                feedback.setCn_feedback_content(map.get("content").toString());
                feedback.setCn_feedback_isReturn(1);
                feedback.setCn_feedback_type(map.get("type").toString());
                feedback.setImageURL(map.get("imageUrl").toString());
                feedback.setCn_user_id(Integer.parseInt(map.get("userId").toString()));
                feedbackDao.save(feedback);
                return ServerResponse.createBySuccess("新增成功", feedback);
            }catch (Exception e){
                logger.error("新增失败");
                return ServerResponse.createByErrorMessage("创建失败");
            }
        }
    }
}
