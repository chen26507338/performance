package com.stylefeng.guns.modular.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.annotion.BussinessLog;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.Const;
import com.stylefeng.guns.common.constant.dictmap.UserDict;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.constant.state.ManagerStatus;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.datascope.DataScope;
import com.stylefeng.guns.core.db.Db;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.DateUtils;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.job.model.Job;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.job.service.IJobService;
import com.stylefeng.guns.modular.pay.model.PaySetting;
import com.stylefeng.guns.modular.pay.service.IPaySettingService;
import com.stylefeng.guns.modular.system.dao.UserMgrDao;
import com.stylefeng.guns.modular.system.decorator.UserDecorator;
import com.stylefeng.guns.modular.system.factory.UserFactory;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.system.transfer.UserDto;
import com.stylefeng.guns.modular.system.warpper.UserWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.naming.NoPermissionException;
import javax.validation.Valid;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 系统管理员控制器
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("${guns.admin-prefix}/mgr")
public class UserMgrController extends BaseController {

    private static String PREFIX = "/system/user/";

    @Resource
    private GunsProperties gunsProperties;

    @Resource
    private UserMgrDao managerDao;
    @Autowired
    private IPaySettingService paySettingService;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IJobService jobService;
    @Autowired
    private IUserService userService;

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("deptList", deptService.selectAllOn());
        return PREFIX + "user.html";
    }

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("/user_add")
    public String addView(Model model) {
        model.addAttribute("deptList", deptService.selectAllOn());
        return PREFIX + "user_add.html";
    }
    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("/open_import")
    public String openImport() {
        return PREFIX + "user_import.html";
    }

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("/import")
    @ResponseBody
    public Object importUser(User user) {
        userService.importUser(user);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("/open_psImport")
    public String openPsImport() {
        return  "/pay/paySetting/paySetting_import.html";
    }

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("/importSetting")
    @ResponseBody
    public Object importSetting(PaySetting paySetting) {
        userService.importSetting(paySetting);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到角色分配页面
     */
    //@RequiresPermissions("/mgr/role_assign")  //利用shiro自带的权限检查
    @Permission
    @RequestMapping("/role_assign/{userId}")
    public String roleAssign(@PathVariable Long userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = Db.create(UserMapper.class).selectOneByCon("id", userId);
        model.addAttribute("userId", userId);
        model.addAttribute("userAccount", user.getAccount());
        return PREFIX + "user_roleassign.html";
    }

    /**
     * 跳转到编辑管理员页面
     */
    @Permission
    @RequestMapping("/user_edit/{userId}")
    public String userEdit(@PathVariable Long userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = this.userMapper.selectById(userId);
        model.addAttribute("deptList", deptService.selectAllOn());
        Job param = new Job();
        param.setDeptId(user.getDeptId());
        param.setStatus(YesNo.YES.getCode());
        model.addAttribute("jobList", jobService.selectList(new EntityWrapper<>(param)));
        model.addAttribute(user);
//        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleId()));
        LogObjectHolder.me().set(user);
        return PREFIX + "user_edit.html";
    }

    /**
     * 跳转到查看用户详情页面
     */
    @RequestMapping("/user_info")
    public String userInfo(Model model) {
        Long userId = ShiroKit.getUser().getId();
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = this.userMapper.selectById(userId);
        model.addAttribute(user);
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleId()));
        LogObjectHolder.me().set(user);
        return PREFIX + "user_view.html";
    }

    /**
     * 跳转到修改密码界面
     */
    @RequestMapping("/user_chpwd")
    public String chPwd() {
        return PREFIX + "user_chpwd.html";
    }

    /**
     * 修改当前用户的密码
     */
    @RequestMapping("/changePwd")
    @ResponseBody
    public Object changePwd(@RequestParam String oldPwd, @RequestParam String newPwd, @RequestParam String rePwd) {
        if (!newPwd.equals(rePwd)) {
            throw new GunsException(BizExceptionEnum.TWO_PWD_NOT_MATCH);
        }
        Long userId = ShiroKit.getUser().getId();
        User user = userMapper.selectById(userId);
        String oldMd5 = ShiroKit.md5(oldPwd, user.getSalt());
        if (user.getPassword().equals(oldMd5)) {
            String newMd5 = ShiroKit.md5(newPwd, user.getSalt());
            user.setPassword(newMd5);
            user.updateById();
            return SUCCESS_TIP;
        } else {
            throw new GunsException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
        }
    }

    /**
     * 查询管理员列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String name, @RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) Integer deptid) {
        Page<User> page = new PageFactory<User>().defaultPage();
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        if (StrUtil.isNotBlank(name)) {
            wrapper.andNew("phone like CONCAT('%',{0},'%')" +
                    " or account like CONCAT('%',{0},'%')" +
                    " or name like CONCAT('%',{0},'%')", name);
        }
        userService.selectPage(page, wrapper);
        page.setRecords(new UserDecorator(page.getRecords()).decorate());
        return packForBT(page);
    }

    /**
     * 添加管理员
     */
    @RequestMapping("/add")
    @BussinessLog(value = "添加职工", key = "account", dict = UserDict.class)
    @ResponseBody
    public Tip add(User user) {

        // 判断账号是否重复
        User theUser = managerDao.getByAccount(user.getAccount());
        if (theUser != null) {
            throw new GunsException(BizExceptionEnum.USER_ALREADY_REG);
        }

        // 完善账号信息
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
        user.setStatus(ManagerStatus.OK.getCode());
        user.setCreateTime(new Date());
        user.setRoleId(IRoleService.TYPE_TEACHER + "");

        this.userMapper.insert(user);
        return SUCCESS_TIP;
    }

    /**
     * 修改管理员
     *
     */
    @RequestMapping("/edit")
    @BussinessLog(value = "修改管理员", key = "account", dict = UserDict.class)
    @ResponseBody
    public Tip edit(User user) {
        userService.updateById(user);
        return SUCCESS_TIP;
    }

    /**
     * 删除管理员（逻辑删除）
     */
    @RequestMapping("/delete")
    @BussinessLog(value = "删除管理员", key = "userId", dict = UserDict.class)
    @Permission
    @ResponseBody
    public Tip delete(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能删除超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new GunsException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        userService.deleteById(userId);
//        assertAuth(userId);
//        this.managerDao.setStatus(userId, ManagerStatus.DELETED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 查看管理员详情
     */
    @RequestMapping("/view/{userId}")
    @ResponseBody
    public User view(@PathVariable Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        return this.userMapper.selectById(userId);
    }

    /**
     * 重置管理员的密码
     */
    @RequestMapping("/reset")
    @BussinessLog(value = "重置管理员密码", key = "userId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip reset(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = this.userMapper.selectById(userId);
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(Const.DEFAULT_PWD, user.getSalt()));
        this.userMapper.updateById(user);
        return SUCCESS_TIP;
    }

    /**
     * 冻结用户
     */
    @RequestMapping("/freeze")
    @BussinessLog(value = "冻结用户", key = "userId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip freeze(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能冻结超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new GunsException(BizExceptionEnum.CANT_FREEZE_ADMIN);
        }
        assertAuth(userId);
        this.managerDao.setStatus(userId, ManagerStatus.FREEZED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "解除冻结用户", key = "userId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip unfreeze(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        this.managerDao.setStatus(userId, ManagerStatus.OK.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 部门用户列表
     */
    @RequestMapping("/dept/list/noPage")
    @ResponseBody
    public Object userDeptListNoPage(@RequestParam Long deptId) {
        User user = new User();
        user.setDeptId(deptId);
        return userService.selectList(new EntityWrapper<>(user));
    }

    /**
     * 分配角色
     */
    @RequestMapping("/setRole")
    @BussinessLog(value = "分配角色", key = "userId,roleIds", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip setRole(@RequestParam("userId") Long userId, @RequestParam("roleIds") String roleIds) {
        if (ToolUtil.isOneEmpty(userId, roleIds)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能修改超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new GunsException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
//        assertAuth(userId);
        this.managerDao.setRoles(userId, roleIds);
        return SUCCESS_TIP;
    }

    /**
     * 上传图片
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture) {
        String fileName = picture.getOriginalFilename();
        String pictureName = DateUtils.getDays() + UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
        try {
            String fileSavePath = gunsProperties.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));
        } catch (Exception e) {
            throw new GunsException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return pictureName;
    }

    /**
     * 判断当前登录的用户是否有操作这个用户的权限
     */
    private void assertAuth(Integer userId) {

    }
}
