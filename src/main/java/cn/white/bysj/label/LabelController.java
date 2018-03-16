package cn.white.bysj.label;

import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.utils.ComponentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-03-02 21:42
 */
@Controller
@RequestMapping(value = "/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @RequestMapping(value = "labelList.do")
    @ResponseBody
    public ServerResponse labelList(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return labelService.labelList(map);
    }

    @RequestMapping(value = "newLabel.do")
    @ResponseBody
    public ServerResponse newLabel(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return labelService.newLabel(map);
    }

    @RequestMapping(value = "deleteLabel.do")
    @ResponseBody
    public ServerResponse deleteLabel(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return labelService.deleteLabel(map);
    }

    @RequestMapping(value = "findLabelByName.do")
    @ResponseBody
    public ServerResponse findLabelByName(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return labelService.findLabelByName(map);
    }

    @RequestMapping(value = "updateLabelName.do")
    @ResponseBody
    public ServerResponse updateLabelName(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return labelService.updateLabelName(map);
    }

}
