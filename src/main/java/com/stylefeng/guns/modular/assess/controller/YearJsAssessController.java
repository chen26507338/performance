package com.stylefeng.guns.modular.assess.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.poi.word.Word07Writer;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.common.utils.DocWriter;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.KaptchaUtil;
import com.stylefeng.guns.modular.assess.decorator.YearJsAssessDecorator;
import com.stylefeng.guns.modular.assess.model.YearJsAssess;
import com.stylefeng.guns.modular.assess.service.IYearJsAssessService;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import fr.opensagres.odfdom.converter.core.utils.IOUtils;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.apache.shiro.authz.annotation.RequiresPermissions;;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.mapper.EntityWrapper;;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import com.stylefeng.guns.modular.assess.model.YearJsAssess;
import com.stylefeng.guns.modular.assess.service.IYearJsAssessService;
import com.stylefeng.guns.modular.assess.decorator.YearJsAssessDecorator;
import sun.security.provider.SHA;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 教师考核控制器
 *
 * @author 
 * @Date 2020-12-21 22:58:22
 */
@Controller
@RequestMapping("${guns.admin-prefix}/yearJsAssess")
public class YearJsAssessController extends BaseController {

    private String PREFIX = "/assess/yearJsAssess/";

    @Autowired
    private IYearJsAssessService yearJsAssessService;
    @Autowired
    private IUserService userService;
    @Resource
    private GunsProperties gunsProperties;

    /**
     * 跳转到教师考核首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/yearJsAssess/list"})
    public String index(int type, Model model) {
        model.addAttribute("type", type);
        model.addAttribute("modelName", ConstantFactory.me().getDictsByName("模板名称", type));
        return PREFIX + "yearJsAssess.html";
    }

    /**
     * 跳转到添加教师考核
     */
    @RequestMapping("/yearJsAssess_add")
    @RequiresPermissions(value = {"/yearJsAssess/add"})
    public String yearJsAssessAdd() {
        return PREFIX + "yearJsAssess_add.html";
    }

    /**
     * 跳转到修改教师考核
     */
    @RequestMapping("/yearJsAssess_update/{yearJsAssessId}")
    @RequiresPermissions(value = {"/yearJsAssess/update"})
    public String yearJsAssessUpdate(@PathVariable String yearJsAssessId, Model model) {
        YearJsAssess yearJsAssess = yearJsAssessService.selectById(yearJsAssessId);
        model.addAttribute("item",yearJsAssess);
        LogObjectHolder.me().set(yearJsAssess);
        return PREFIX + "yearJsAssess_edit.html";
    }

    /**
     * 获取教师考核列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/yearJsAssess/list"})
    @ResponseBody
    public Object list(YearJsAssess yearJsAssess) {
        Page<YearJsAssess> page = new PageFactory<YearJsAssess>().defaultPage();
        EntityWrapper< YearJsAssess> wrapper = new EntityWrapper<>();
        if (yearJsAssess.getType() != null) {
            wrapper.eq("type", yearJsAssess.getType());
        }

        List<Long> roles = ShiroKit.getUser().getRoleList();
        if (!roles.contains(IRoleService.TYPE_HR_HANDLER) &&
                (!roles.contains(IRoleService.TYPE_HR_HANDLER) && ShiroKit.getUser().deptId != IDeptService.HR)) {
            wrapper.eq("user_id", ShiroKit.getUser().id);
        }
        yearJsAssessService.selectPage(page,wrapper);
        page.setRecords(new YearJsAssessDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增教师考核
     */
    @RequestMapping(value = "/add")
    @RequiresPermissions(value = {"/yearJsAssess/add"})
    @ResponseBody
    public Object add(YearJsAssess yearJsAssess) {
        yearJsAssessService.insert(yearJsAssess);
        return SUCCESS_TIP;
    }

    /**
     * 删除教师考核
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/yearJsAssess/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long yearJsAssessId) {
        yearJsAssessService.deleteById(yearJsAssessId);
        return SUCCESS_TIP;
    }

    /**
     * 修改教师考核
     */
    @RequestMapping(value = "/update")
    @RequiresPermissions(value = {"/yearJsAssess/update"})
    @ResponseBody
    public Object update(YearJsAssess yearJsAssess) {
        yearJsAssessService.updateById(yearJsAssess);
        return SUCCESS_TIP;
    }

    /**
     * 教师考核详情
     */
    @RequestMapping(value = "/detail/{yearJsAssessId}")
    @ResponseBody
    public Object detail(@PathVariable("yearJsAssessId") String yearJsAssessId) {
        return yearJsAssessService.selectById(yearJsAssessId);
    }

    /**
     * 考核申请
     */
    @RequestMapping("/yearJsAssess_apply/{type}")
    public String applyApproval(@PathVariable int type,Model model) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.like("role_id", IRoleService.TYPE_JYSZR + "");
        wrapper.eq("dept_id", ShiroKit.getUser().deptId);
        model.addAttribute("users", userService.selectList(wrapper));
        model.addAttribute("type", type);
        model.addAttribute("modelName", ConstantFactory.me().getDictsByName("模板名称", type));
        model.addAttribute("fileName", ConstantFactory.me().getDictsByName("模板文件", type));
        return PREFIX + "yearJsAssess_apply.html";
    }
    /**
     * 考核申请
     */
    @RequestMapping(value = "/previewDoc/{fileName}.{exts}")
    public void previewDoc(@PathVariable String fileName, @PathVariable String exts) {
        String path = gunsProperties.getFileUploadPath() + fileName + "." + exts;

        try {
            XWPFDocument xwpfDocument = new XWPFDocument(FileUtil.getInputStream(path));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            XHTMLConverter.getInstance().convert(xwpfDocument, outputStream, null);
            HttpServletResponse response = HttpKit.getResponse();
            response.setHeader("Content-Type","text/html;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(outputStream.toString());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/downloadTemplate/{template}/{fileName}")
    public void downloadTemplate(@PathVariable String template,@PathVariable String fileName) {
        try {
            InputStream inputStream = ResourceUtil.getStream(StrUtil.format("doc/{}.docx", template));
//            File file = new File(ResourceUtil.getResource(StrUtil.format("doc/{}.docx", template)).toURI());
//        File file = classPathResource.getFile();
//            byte[] bytes = FileUtil.readBytes(ResourceUtil.getResource(StrUtil.format("doc/{}.docx", template)).getPath());

            HttpServletResponse response = HttpKit.getResponse();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //弹出下载对话框的文件名，不能为中文，中文请自行编码
            response.setHeader("Content-Disposition", "attachment;filename=" + URLUtil.encode(fileName) + ".doc");
            IOUtils.copy(inputStream, response.getOutputStream());
//            response.getOutputStream().write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/downloadDoc/{id}")
    public void downloadDoc(@PathVariable Long id) {
        YearJsAssess yearJsAssess = yearJsAssessService.selectById(id);
        String path = gunsProperties.getFileUploadPath() + yearJsAssess.getKygz();
        Map<String, String> map = new HashMap<>();
        map.put("${comments}", yearJsAssess.getComments());
        map.put("${level}", yearJsAssess.getLevel());
        map.put("${year}", yearJsAssess.getYear());
        map.put("${sjcom}", yearJsAssess.getSjcom());
        map.put("${jyscom}", yearJsAssess.getJyscom());
        try {
            User user = userService.selectIgnorePointById(yearJsAssess.getUserId());
            HttpServletResponse response = HttpKit.getResponse();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //弹出下载对话框的文件名，不能为中文，中文请自行编码
            response.setHeader("Content-Disposition", "attachment;filename=" + URLUtil.encode(
                    user.getName() + "-" + ConstantFactory.me().getDictsByName("模板名称", yearJsAssess.getType()) + "文档") + ".doc");

            DocWriter.searchAndReplace(path,response.getOutputStream(),map);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        File file = classPathResource.getFile();
//        byte[] bytes = FileUtil.readBytes(file);
//
//        try {
//            response.getOutputStream().write(bytes);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    
    /**
     * 考核申请
     */
    @RequestMapping(value = "/act/apply")
    @ResponseBody
    public Object actApply(YearJsAssess yearJsAssess) {
        yearJsAssessService.apply(yearJsAssess);
        return SUCCESS_TIP;
    }

    /**
     * 考核审核
     */
    @RequestMapping(value = "/act/audit")
    @ResponseBody
    public Object audit(YearJsAssess yearJsAssess) {
        yearJsAssessService.audit(yearJsAssess);
        return SUCCESS_TIP;
    }

    /**
     * 流程数据
     */
    @RequestMapping("/act/data")
    @ResponseBody
    public Object procData(YearJsAssess yearJsAssess) {
        YearJsAssess params = new YearJsAssess();
        params.setStatus(YesNo.NO.getCode());
        params.setProcInsId(yearJsAssess.getAct().getProcInsId());
        List<Map<String, Object>> datas = new YearJsAssessDecorator(yearJsAssessService.selectList(new EntityWrapper<>(params))).decorateMaps();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", datas);
        return result;
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/act")
    public String act(YearJsAssess yearJsAssess, Model model) {
        yearJsAssess.setProcInsId(yearJsAssess.getAct().getProcInsId());
        EntityWrapper<YearJsAssess> wrapper = new EntityWrapper<>(yearJsAssess);
//        wrapper.last("limit 1");
        YearJsAssess data = yearJsAssess.selectOne(wrapper);
        model.addAttribute("user", userService.selectIgnorePointById(data.getUserId()));
        model.addAttribute("item", data);
        model.addAttribute("act", yearJsAssess.getAct());
        return PREFIX + "yearJsAssess_audit.html";
    }
}
