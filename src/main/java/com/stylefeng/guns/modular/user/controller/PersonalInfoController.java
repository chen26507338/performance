package com.stylefeng.guns.modular.user.controller;

import cn.hutool.core.util.URLUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.constant.factory.GroupTemplateFactory;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
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
import com.stylefeng.guns.modular.user.model.EducationExperience;
import com.stylefeng.guns.modular.user.model.Kinship;
import com.stylefeng.guns.modular.user.model.PersonalInfo;
import com.stylefeng.guns.modular.user.model.RewardsPunishment;
import com.stylefeng.guns.modular.user.service.IEducationExperienceService;
import com.stylefeng.guns.modular.user.service.IKinshipService;
import com.stylefeng.guns.modular.user.service.IPersonalInfoService;
import com.stylefeng.guns.modular.user.service.IRewardsPunishmentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.beetl.core.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * 跳转到自然信息首页
     */
    @RequestMapping("")
    @RequiresPermissions(value = {"/personalInfo/list"})
    public String index(Model model) {
        PersonalInfo params = new PersonalInfo();
        params.setStatus(YesNo.YES.getCode());
        params.setUserId(ShiroKit.getUser().id);
        PersonalInfo item = personalInfoService.selectOne(new EntityWrapper<>(params));
        if (item != null) {
            User user = userService.selectIgnorePointById(item.getUserId());
            Dept dept = deptService.selectById(user.getDeptId());
            model.addAttribute("dept", dept);
        }
        model.addAttribute("item", item);
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
        model.addAttribute("item",personalInfo);
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
        EntityWrapper< PersonalInfo> wrapper = new EntityWrapper<>();
        personalInfoService.selectPage(page,wrapper);
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
        model.addAttribute("item", item);
        model.addAttribute("user", user);
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
        Template template = GroupTemplateFactory.getClasspathResourceTemplate().getTemplate("doc/cadre_register.xml");

        //自然信息
        User user = userService.selectById(ShiroKit.getUser().id);
        user.putExpand("sex", ConstantFactory.me().getDictsByName("性别", user.getSex()));
        if (user.getBirthday() != null) {
            user.putExpand("birthday", DateUtils.format(user.getBirthday(),"yyyy.MM"));
        }
        if (user.getJobId() != null) {
            Job job = jobService.selectById(user.getJobId());
            user.putExpand("job", job.getName());
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
        template.binding("rps", rewardsPunishments);

        //家庭成员
        Kinship ksParams = new Kinship();
        ksParams.setUserId(ShiroKit.getUser().id);
        ksParams.setStatus(YesNo.YES.getCode());
        List<Kinship> kinships = kinshipService.selectList(new EntityWrapper<>(ksParams));
        template.binding("kinships", new KinshipDecorator(kinships).decorate());

        HttpServletResponse response = HttpKit.getResponse();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=" + URLUtil.encode("干部登记表") + ".doc");
        template.renderTo(response.getWriter());
    }

}
