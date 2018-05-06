package cn.white.bysj.feedback;

import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.utils.ComponentHelper;
import cn.white.bysj.utils.Cors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-04-01 21:56
 */
@RestController
@RequestMapping(value = "/feedback")
public class FeedbackController extends Cors {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping(value = "newFeedBack.do")
    public ServerResponse newFeedBack(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return feedbackService.newFeedBack(map);
    }


}
