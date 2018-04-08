package cn.white.bysj.feedback;

import cn.white.bysj.commons.ServerResponse;

import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-04-01 21:56
 */
public interface FeedbackService {

    ServerResponse  newFeedBack(Map<String,Object> map);
}
