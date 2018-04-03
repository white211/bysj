package cn.white.bysj.feedback;

import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



}
