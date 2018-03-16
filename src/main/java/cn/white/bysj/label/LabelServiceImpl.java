package cn.white.bysj.label;

import cn.white.bysj.commons.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-03-02 21:43
 */
@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelDao labelDao;


    @Override
    public ServerResponse labelList(Map<String, Object> map) {
        return null;
    }

    @Override
    public ServerResponse newLabel(Map<String, Object> map) {
        return null;
    }

    @Override
    public ServerResponse deleteLabel(Map<String, Object> map) {
        return null;
    }

    @Override
    public ServerResponse findLabelByName(Map<String, Object> map) {
        return null;
    }

    @Override
    public ServerResponse updateLabelName(Map<String, Object> map) {
        return null;
    }
}
