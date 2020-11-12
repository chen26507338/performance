package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.system.dao.NoticeDao;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 总览信息
 *
 * @author fengshuonan
 * @Date 2017年3月4日23:05:54
 */
@Controller
@RequestMapping("${guns.admin-prefix}/blackboard")
public class BlackboardController extends BaseController {

    @Autowired
    private IUserService userService;


    /**
     * 跳转到黑板
     */
    @RequestMapping("")
    public String blackboard(Model model) {
//        List<Map<String, Object>> notices = noticeDao.list(null);
//        model.addAttribute("noticeList",notices);
        User user = userService.selectById(ShiroKit.getUser().id);
        model.addAttribute("item", user);
        return "/blackboard.html";
    }
}
