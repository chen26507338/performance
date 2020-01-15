package com.stylefeng.guns.modular.code.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.generator.action.config.WebGeneratorConfig;
import com.stylefeng.guns.generator.action.model.GenQo;
import com.stylefeng.guns.generator.action.model.GenTable;
import com.stylefeng.guns.generator.action.model.GenTableColumn;
import com.stylefeng.guns.modular.code.factory.DefaultTemplateFactory;
import com.stylefeng.guns.modular.code.model.InterfaceGen;
import com.stylefeng.guns.modular.system.service.IGenTableService;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.Comparator;

//import com.stylefeng.guns.core.datasource.DruidProperties;

/**
 * 代码生成控制器
 *
 * @author fengshuonan
 * @Date 2017年11月30日16:39:19
 */
@Controller
@RequestMapping("${guns.admin-prefix}/code")
public class CodeController extends BaseController {

    private static String PREFIX = "/code";


    @Resource
    private DruidDataSource druidDataSource;

    @Autowired
    private IGenTableService genTableService;

    /**
     * 跳转到代码生成主页
     */
    @RequestMapping("")
    public String blackboard(GenTable genTable, Model model) {
        model.addAttribute("table", genTableService.selectById(genTable.getId()));
        model.addAttribute("params", DefaultTemplateFactory.getDefaultParams());
        model.addAttribute("templates", DefaultTemplateFactory.getDefaultTemplates());
        return PREFIX + "/code.html";
    }

    /**
     * 生成代码
     */
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseBody
    public Object generate(GenQo genQo) {
        genQo.setUrl(druidDataSource.getUrl());
        genQo.setUserName(druidDataSource.getUsername());
        genQo.setPassword(druidDataSource.getPassword());
        genQo.setGenTable(genTableService.getGenTableAndColumn(genQo.getGenTable().getId()));
        genQo.getGenTable().getGenTableColumns().sort(Comparator.comparing(GenTableColumn::getSort));
        WebGeneratorConfig webGeneratorConfig = new WebGeneratorConfig(genQo);
        webGeneratorConfig.doMpGeneration();
        webGeneratorConfig.doAdiGeneration();
        return SUCCESS_TIP;
    }

    @RequestMapping("/preview")
    public String preview(GenQo genQo, Model model) {
        genQo.setUrl(druidDataSource.getUrl());
        genQo.setUserName(druidDataSource.getUsername());
        genQo.setPassword(druidDataSource.getPassword());
        genQo.setGenTable(genTableService.getGenTableAndColumn(genQo.getGenTable().getId()));
        WebGeneratorConfig webGeneratorConfig = new WebGeneratorConfig(genQo);
        model.addAttribute("pageInfo", HtmlUtils.htmlEscape(webGeneratorConfig.getPreview()));
        model.addAttribute("codeType", genQo.getCodeType());
        return PREFIX + "/codePreview.html";
    }

    @RequestMapping("/interface")
    public String interfaceGen() {
        return PREFIX + "/interface.html";
    }

    @RequestMapping("/interface/gen")
    public String interfaceGenView(InterfaceGen interfaceGen,Model model) {
        GroupTemplate groupTemplate = new GroupTemplate();
        Template pageTemplate = groupTemplate.getTemplate("gunsTemplate/advanced/interface_func.btl");
        interfaceGen.setLowClassName(interfaceGen.getClassName().toLowerCase());
        pageTemplate.binding("interface", interfaceGen);

        model.addAttribute("pageInfo", pageTemplate.render());
        model.addAttribute("codeType", "java");
        return PREFIX + "/codePreview.html";
    }

}
