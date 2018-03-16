package cn.white.bysj.label;

import cn.white.bysj.commons.ServerResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-03-02 21:42
 */

public interface LabelService {

    ServerResponse labelList(Map<String,Object> map);

    ServerResponse newLabel(Map<String,Object> map);

    ServerResponse deleteLabel(Map<String,Object> map);

    ServerResponse findLabelByName(Map<String,Object> map);

    ServerResponse updateLabelName(Map<String,Object> map);

}

