package com.stylefeng.guns.modular.user.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.GunsApplication;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.constant.factory.GroupTemplateFactory;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.DateUtils;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.model.Job;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.job.service.IJobService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.user.decorator.EducationExperienceDecorator;
import com.stylefeng.guns.modular.user.decorator.KinshipDecorator;
import com.stylefeng.guns.modular.user.decorator.PersonalInfoDecorator;
import com.stylefeng.guns.modular.user.model.*;
import com.stylefeng.guns.modular.user.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.beetl.core.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

;
;

/**
 * 自然信息控制器
 *
 * @author cp
 * @Date 2020-07-06 10:02:41
 */
@Controller
@RequestMapping("${guns.admin-prefix}/personalInfo")
public class PersonalInfoController extends BaseController {

    private String PREFIX = "/user/personalInfo/";

    @Autowired
    private IPersonalInfoService personalInfoService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IJobService jobService;
    @Autowired
    private IEducationExperienceService educationExperienceService;
    @Autowired
    private IRewardsPunishmentService rewardsPunishmentService;
    @Autowired
    private IKinshipService kinshipService;
    @Autowired
    private IWorkResumeService workResumeService;
    @Resource
    private GunsProperties gunsProperties;

    /**
     * 跳转到自然信息首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/personalInfo/list"})
    public String index(Model model) {
        User user = userService.selectById(ShiroKit.getUser().id);
        Dept dept = deptService.selectById(user.getDeptId());
        model.addAttribute("dept", dept);
        model.addAttribute("item", user);
        return PREFIX + "personalInfo.html";
    }

    /**
     * 跳转到添加自然信息
     */
    @RequestMapping("/personalInfo_add")
    public String personalInfoAdd(Model model) {
        PersonalInfo params = new PersonalInfo();
        params.setStatus(YesNo.YES.getCode());
        params.setUserId(ShiroKit.getUser().id);
        PersonalInfo item = personalInfoService.selectOne(new EntityWrapper<>(params));
        model.addAttribute("item", item);
        return PREFIX + "personalInfo_add.html";
    }

    /**
     * 跳转到修改自然信息
     */
    @RequestMapping("/personalInfo_update/{personalInfoId}")
    @RequiresPermissions(value = {"/personalInfo/update"})
    public String personalInfoUpdate(@PathVariable String personalInfoId, Model model) {
        PersonalInfo personalInfo = personalInfoService.selectById(personalInfoId);
        model.addAttribute("item", personalInfo);
        LogObjectHolder.me().set(personalInfo);
        return PREFIX + "personalInfo_edit.html";
    }

    /**
     * 获取自然信息列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"/personalInfo/list"})
    @ResponseBody
    public Object list(PersonalInfo personalInfo) {
        Page<PersonalInfo> page = new PageFactory<PersonalInfo>().defaultPage();
        EntityWrapper<PersonalInfo> wrapper = new EntityWrapper<>();
        personalInfoService.selectPage(page, wrapper);
        page.setRecords(new PersonalInfoDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 新增自然信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PersonalInfo personalInfo) {
        personalInfoService.addApply(personalInfo);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到流程审核
     */
    @RequestMapping("/personalInfo_act")
    public String personalInfoAct(PersonalInfo personalInfo, Model model) {
        model.addAttribute("act", personalInfo.getAct());
        PersonalInfo params = new PersonalInfo();
        params.setProcInsId(personalInfo.getAct().getProcInsId());
        EntityWrapper<PersonalInfo> wrapper = new EntityWrapper<>(params);
        PersonalInfo item = personalInfoService.selectOne(wrapper);
        User user = userService.selectIgnorePointById(item.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("item", item);
        return PREFIX + "personalInfo_edit.html";
    }

    /**
     * 删除自然信息
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value = {"/personalInfo/delete"})
    @ResponseBody
    public Object delete(@RequestParam Long personalInfoId) {
        personalInfoService.deleteById(personalInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改自然信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PersonalInfo personalInfo) {
        personalInfoService.audit(personalInfo);
        return SUCCESS_TIP;
    }

    /**
     * 自然信息详情
     */
    @RequestMapping(value = "/detail/{personalInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("personalInfoId") String personalInfoId) {
        return personalInfoService.selectById(personalInfoId);
    }

    /**
     * 导出干部登记表
     */
    @RequestMapping("/exportCadreRegister")
    @ResponseBody
    public void exportCadreRegister() throws IOException {
        User user = userService.selectById(ShiroKit.getUser().id);

        Template template = GroupTemplateFactory.getClasspathResourceTemplate().getTemplate("doc/cadre_register.xml");
        this.setPersonInfo(user,template);

        HttpServletResponse response = HttpKit.getResponse();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=" + URLUtil.encode("干部登记表-" + user.getName()) + ".doc");
        template.renderTo(response.getWriter());
    }

    /**
     * 导出干部审批表
     */
    @RequestMapping("/exportAppoint")
    @ResponseBody
    public void exportCadreAppoint() throws IOException {
        User user = userService.selectById(ShiroKit.getUser().id);
        Template template = GroupTemplateFactory.getClasspathResourceTemplate().getTemplate("doc/cadre_appoint.xml");
        this.setPersonInfo(user,template);

        HttpServletResponse response = HttpKit.getResponse();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=" + URLUtil.encode("干部审批表-" + user.getName()) + ".doc");
        template.renderTo(response.getWriter());
    }

    private void setPersonInfo(User user,Template template) {
        //自然信息
        user.putExpand("sex", ConstantFactory.me().getDictsByName("性别", user.getSex()));
        if (user.getBirthday() != null) {
            user.putExpand("birthday", DateUtils.format(user.getBirthday(), "yyyy.MM"));
        }
        if (user.getJoinPartyTime() != null) {
            user.putExpand("joinPartyTime", DateUtils.format(user.getJoinPartyTime(), "yyyy.MM"));
        }
        if (user.getFirstWorkTime() != null) {
            user.putExpand("firstWorkTime", DateUtils.format(user.getFirstWorkTime(), "yyyy.MM"));
        }
        if (user.getJobId() != null) {
            Job job = jobService.selectById(user.getJobId());
            user.putExpand("job", job.getName());
        }
        if (StrUtil.isNotBlank(user.getAvatar())) {
            String userPhoto = Base64.encode(new File(gunsProperties.getFileUploadPath() + user.getAvatar()));
            template.binding("userPhoto", userPhoto);
        }
        template.binding("user", user);

        //学历学位
        EducationExperience eduParams = new EducationExperience();
        eduParams.setUserId(user.getId());
        eduParams.setStatus(YesNo.YES.getCode());
        List<EducationExperience> educationExperiences = educationExperienceService.selectList(new EntityWrapper<>(eduParams));
        template.binding("edus", new EducationExperienceDecorator(educationExperiences).decorate());

        //奖惩
        RewardsPunishment rpParams = new RewardsPunishment();
        rpParams.setUserId(ShiroKit.getUser().id);
        rpParams.setStatus(YesNo.YES.getCode());
        List<RewardsPunishment> rewardsPunishments = rewardsPunishmentService.selectList(new EntityWrapper<>(rpParams));
        for (RewardsPunishment rewardsPunishment : rewardsPunishments) {
            //调整日期格式
            if (StrUtil.isNotBlank(rewardsPunishment.getTime())) {
                String[] date = rewardsPunishment.getTime().split("-");
                rewardsPunishment.setTime(date[0] + "年" + date[1] + "月");
            }
        }
        template.binding("rps", rewardsPunishments);

        //工作简历
        WorkResume wrParams = new WorkResume();
        wrParams.setUserId(ShiroKit.getUser().id);
        wrParams.setStatus(YesNo.YES.getCode());
        List<WorkResume> workResumes = workResumeService.selectList(new EntityWrapper<>(wrParams));
        for (WorkResume workResume : workResumes) {
            //调整日期格式
            if (StrUtil.isNotBlank(workResume.getStartDate())) {
                String[] date = workResume.getStartDate().split("-");
                workResume.setStartDate(date[0] + "." + date[1]);
            }
            if (StrUtil.isNotBlank(workResume.getEndDate())) {
                String[] date = workResume.getEndDate().split("-");
                workResume.setEndDate(date[0] + "." + date[1]);
            }
        }
        template.binding("wrs", workResumes);

        //家庭成员
        Kinship ksParams = new Kinship();
        ksParams.setUserId(ShiroKit.getUser().id);
        ksParams.setStatus(YesNo.YES.getCode());
        List<Kinship> kinships = kinshipService.selectList(new EntityWrapper<>(ksParams));
        for (Kinship kinship : kinships) {
            //调整出生年月格式
            if (StrUtil.isNotBlank(kinship.getBirthday())) {
                kinship.setBirthday(kinship.getBirthday().substring(0, kinship.getBirthday().lastIndexOf("-")).replace("-", "."));
            }
        }
        template.binding("kinships", new KinshipDecorator(kinships).decorate());
    }
}
