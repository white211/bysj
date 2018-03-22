package cn.white.bysj.label;

import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.note.NoteDao;
import cn.white.bysj.utils.FirstFontUtil;
import cn.white.bysj.utils.ValidatorUtil;
import cn.white.bysj.vo.LabelListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

/**
 * Create by @author white
 *
 * @date 2018-03-02 21:43
 */
@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private NoteDao noteDao;

    /**
     * TODO: 标签列表
     * @author white
     * @date 2018-03-17 11:30
       @param "userId"
     * @return
     * @throws
     */
    @Override
    public ServerResponse<List<LabelListVo>> labelList(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId");
        if (ValidatorUtil.validator(map,list).size()>0){
            return ServerResponse.createByErrorMessage("缺少参数，必须包括userId");
        }
        if (StringUtils.isBlank(map.get("userId").toString())){
            return ServerResponse.createByErrorMessage("用户id不能为空");
        }else {
            int userId = Integer.parseInt(map.get("userId").toString());
            List<LabelListVo> labelListVo = new ArrayList<>();
            List<Label> labelList = labelDao.findLabelByUserId(userId);
            for(Label label:labelList){
                LabelListVo labelVo = new LabelListVo();
                labelVo.setCn_user_id(label.getCn_user_id());
                labelVo.setCn_label_id(label.getCn_label_id());
                labelVo.setCn_label_desc(label.getCn_label_desc());
                labelVo.setCn_label_name(label.getCn_label_name());
                labelVo.setCn_label_createTime(label.getCn_label_createTime());
                labelVo.setCn_label_last_updateTime(label.getCn_label_last_updateTime());
                //计算该标签下tyoe=1的笔记条数
                int count = noteDao.countByLabelId(label.getCn_label_id());
                labelVo.setNoteCount(count);
                //提取收个字
                String firstFont = FirstFontUtil.getFirstFont(label.getCn_label_name());
                labelVo.setLabel_first_font(firstFont);
                labelListVo.add(labelVo);
            }

            return ServerResponse.createBySuccess("标签列表",labelListVo);
        }
    }

    /**
     * TODO: 新增标签
     * @author white
     * @date 2018-03-17 11:30
       @param "userId,labelName"
     * @return
     * @throws
     */
    @Override
    public ServerResponse<Label> newLabel(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId","labelName");
        if (ValidatorUtil.validator(map,list).size()>0){
            return ServerResponse.createByErrorMessage("缺少参数，包括userId，labelName");
        }
        if (StringUtils.isBlank(map.get("userId").toString())){
           return ServerResponse.createByErrorMessage("用户id不能为空");
        }else if (StringUtils.isBlank(map.get("labelName").toString())){
          return ServerResponse.createByErrorMessage("标签不能为空");
        }else{
            int userId = Integer.parseInt(map.get("userId").toString());
            int count = labelDao.findLabelByName(map.get("labelName").toString(),userId);
            if (count>0){
               return ServerResponse.createByErrorMessage("该标签已经存在");
            }
            Label label = new Label();
            label.setCn_label_name(map.get("labelName").toString());
            label.setCn_user_id(userId);
            label.setCn_label_createTime(new Date());
            label.setCn_label_last_updateTime(new Date());
            labelDao.save(label);
            return ServerResponse.createBySuccess("新增成功",label);
        }
    }

    /**
     * TODO: 删除标签
     * @author white
     * @date 2018-03-17 12:35
       @param "userId,labelId"
     * @return
     * @throws
     */
    @Override
    public ServerResponse deleteLabel(Map<String, Object> map) {
       List<String> list = Arrays.asList("userId","labelId");
       if(ValidatorUtil.validator(map,list).size()>0){
           return ServerResponse.createByErrorMessage("缺少参数，应包括userId,labelId");
       }
       if (StringUtils.isBlank(map.get("userId").toString())){
           return ServerResponse.createByErrorMessage("用户id不能为空");
       }else if (StringUtils.isBlank(map.get("labelId").toString())){
           return ServerResponse.createByErrorMessage("标签id不能为空");
       }else{
           int userId = Integer.parseInt(map.get("userId").toString());
           int labelId = Integer.parseInt(map.get("labelId").toString());

           labelDao.deleteLabelByUserIdAndLabelId(userId,labelId);
           return ServerResponse.createBySuccessMessags("删除成功");
       }

    }

    /**
     * TODO: 模糊查询标签
     * @author white
     * @date 2018-03-17 13:04
       @param "userId,searchText"
     * @return 
     * @throws 
     */
    @Override
    public ServerResponse<List<LabelListVo>> findLabelByName(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId","searchText");
        if(ValidatorUtil.validator(map,list).size()>0){
            return ServerResponse.createByErrorMessage("缺少参数，应包括userId,searchText");
        }
        if (StringUtils.isBlank(map.get("userId").toString())){
            return ServerResponse.createByErrorMessage("用户id不能为空");
        }else if (StringUtils.isBlank(map.get("searchText").toString())){
            return ServerResponse.createByErrorMessage("查询内容不能为空");
        }else{
            int userId = Integer.parseInt(map.get("userId").toString());
            List<LabelListVo> labelListVo = new ArrayList<>();
            List<Label> labelList = labelDao.findLabelByName(userId,map.get("searchText").toString());
            for(Label label:labelList){
                LabelListVo labelVo = new LabelListVo();
                labelVo.setCn_user_id(label.getCn_user_id());
                labelVo.setCn_label_id(label.getCn_label_id());
                labelVo.setCn_label_desc(label.getCn_label_desc());
                labelVo.setCn_label_name(label.getCn_label_name());
                labelVo.setCn_label_createTime(label.getCn_label_createTime());
                labelVo.setCn_label_last_updateTime(label.getCn_label_last_updateTime());
                //计算该标签下tyoe=1的笔记条数
                int count = noteDao.countByLabelId(label.getCn_label_id());
                labelVo.setNoteCount(count);
                //提取收个字
                String firstFont = FirstFontUtil.getFirstFont(label.getCn_label_name());
                labelVo.setLabel_first_font(firstFont);
                labelListVo.add(labelVo);
            }

            return ServerResponse.createBySuccess("列表",labelListVo);
        }
    }

    /**
     * TODO: 重命名标签
     * @author white
     * @date 2018-03-17 13:17
       @param "userId,labelId，newName"
     * @return
     * @throws
     */
    @Override
    public ServerResponse updateLabelName(Map<String, Object> map) {
        List<String> list = Arrays.asList("userId","labelId","newName");
        if (ValidatorUtil.validator(map,list).size()>0){
            return ServerResponse.createByErrorMessage("缺少参数，应包含userId,labelId,newName");
        }
        if (StringUtils.isBlank(map.get("userId").toString())){
            return ServerResponse.createByErrorMessage("用户id不能为空");
        }else if (StringUtils.isBlank(map.get("labelId").toString())){
            return ServerResponse.createByErrorMessage("标签id不能为空");
        }else{
            int userId = Integer.parseInt(map.get("userId").toString());
            int labelId = Integer.parseInt(map.get("labelId").toString());
            int count = labelDao.findLabelByNameAndUserId(userId,map.get("newName").toString(),labelId);
            if (count>1){
               return ServerResponse.createByErrorMessage("新名称已经存在");
            }else{
                labelDao.updateLabelName(userId,map.get("newName").toString(),labelId);
               return ServerResponse.createBySuccessMessags("重命名成功");
            }
        }
    }
}
