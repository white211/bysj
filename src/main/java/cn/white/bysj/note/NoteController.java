package cn.white.bysj.note;

import cn.white.bysj.admin.controller.BaseController;
import cn.white.bysj.admin.dao.INoteDao;
import cn.white.bysj.admin.service.INoteService;
import cn.white.bysj.admin.vo.NoteVo;
import cn.white.bysj.commons.Constant;
import cn.white.bysj.commons.ServerResponse;
import cn.white.bysj.utils.ComponentHelper;
import cn.white.bysj.utils.Cors;
import cn.white.bysj.utils.QiNiu.QiniuService;
import cn.white.bysj.utils.UUIDutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2018-03-02 21:36
 */

@Controller
@RequestMapping(value = "/note")
public class NoteController
//        extends BaseController
        extends Cors
{

    @Autowired
    private NoteServiceImpl noteService;

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    private INoteService iNoteService;

    @Autowired
    private INoteDao iNoteDao;

    @RequestMapping(value = "noteList.do")
    @ResponseBody
    public ServerResponse noteList(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return noteService.noteList(map);
    }


    @RequestMapping(value = "newNote.do")
    @ResponseBody
    public ServerResponse newNote(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return noteService.newNote(map);
    }

    @RequestMapping(value = "updateNoteTypeId.do")
    @ResponseBody
    public ServerResponse updateNoteTypeId(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return noteService.updateNoteTypeId(map);
    }

    @RequestMapping(value = "findNoteByTitleOrContent.do")
    @ResponseBody
    public ServerResponse findNoteByTitleOrContent(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return noteService.findNoteByTitleOrContent(map);
    }

    @RequestMapping(value = "updateNote.do")
    @ResponseBody
    public ServerResponse updateNote(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return noteService.updateNote(map);
    }

    @RequestMapping(value = "findNoteById.do")
    @ResponseBody
    public ServerResponse findNoteById(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return noteService.findNoteById(map);
    }

    @RequestMapping(value = "addRead.do")
    @ResponseBody
    public ServerResponse addRead(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return noteService.addRead(map);
    }

    @RequestMapping(value = "findNoteByTypeId.do")
    @ResponseBody
    public ServerResponse findNoteByTypeId(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return noteService.findNoteByTypeId(map);
    }

    @RequestMapping(value = "deleteByNoteId.do")
    @ResponseBody
    public ServerResponse deleteByNoteId(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return noteService.deleteByNoteId(map);
    }

    /**
     * TODO: 用于上传图片到七牛云
     *
     * @param "图片文件file"
     * @return
     * @throws
     * @author white
     * @date 2018-03-22 21:27
     */
    @RequestMapping(value = "uploadFile.do")
    @ResponseBody
    public ServerResponse uploadFile(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return ServerResponse.createByErrorMessage("文件不能为空");
        } else {
            try {
                // 包含原始文件名的字符串
                String fi = file.getOriginalFilename();
                // 提取文件拓展名
                String fileNameExtension = fi.substring(fi.indexOf("."), fi.length());
                // 生成云端的真实文件名
                String remoteFileName = UUIDutils.getUUID().toString() + fileNameExtension;
                qiniuService.upload(file.getBytes(), remoteFileName);
                String url = Constant.QINIU_DOMAIN_NAME + remoteFileName;
                return ServerResponse.createBySuccess("上传成功", url);
            } catch (Exception e) {
                e.printStackTrace();
                return ServerResponse.createByErrorMessage("上传出错");
            }
        }
    }


    /**
     * TODO: 通过笔记本id查找笔记
     *
     * @return
     * @throws
     * @author white
     * @date 2018-04-04 13:39
     */
    @RequestMapping(value = "findNoteByBookId.do")
    @ResponseBody
    public ServerResponse findNoteByBookId(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return noteService.findNoteByBookId(map);
    }


    /**
     * TODO: 通过标签id查找笔记
     *
     * @return
     * @throws
     * @author white
     * @date 2018-04-04 14:56
     */
    @RequestMapping(value = "findNoteByTagId.do")
    @ResponseBody
    public ServerResponse findNoteByTagId(HttpServletRequest request) {
        Map<String, Object> map = ComponentHelper.requestToMap(request);
        return noteService.findNoteByTagId(map);
    }

    @RequestMapping(value = "updateEncrypt.do")
    @ResponseBody
    public ServerResponse updateEncrypt(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return noteService.updateEncrypt(map);
    }

    @RequestMapping(value = "checkReadPassword.do")
    @ResponseBody
    public ServerResponse checkReadPassword(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return noteService.checkReadPassword(map);
    }

    @RequestMapping(value = "updateNoteIsShare.do")
    @ResponseBody
    public ServerResponse updateNoteIsShare(HttpServletRequest request){
        Map<String,Object> map = ComponentHelper.requestToMap(request);
        return noteService.updateNoteIsShare(map);
    }

}


