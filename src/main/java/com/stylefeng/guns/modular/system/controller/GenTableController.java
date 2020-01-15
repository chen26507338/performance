package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.generator.action.model.GenTableColumn;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.generator.action.model.GenQo;
import com.stylefeng.guns.modular.code.service.TableService;
import com.stylefeng.guns.modular.system.service.IGenTableColumnService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.generator.action.model.GenTable;
import com.stylefeng.guns.modular.system.service.IGenTableService;

import java.util.ArrayList;

/**
 * 业务表控制器
 *
 * @author fengshuonan
 * @Date 2018-01-13 18:19:26
 */
@Controller
@RequestMapping("${guns.admin-prefix}/genTable")
public class GenTableController extends BaseController {

    private String PREFIX = "/system/genTable/";

    @Autowired
    private IGenTableService genTableService;

    @Autowired
    private TableService tableService;

    /**
     * 跳转到业务表首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("tables", tableService.getAllTables());
        return PREFIX + "genTable.html";
    }

    /**
     * 跳转到添加业务表
     */
    @RequestMapping("/genTable_add")
    public String genTableAdd(GenQo genQo, Model model) {
        EntityWrapper<GenTable> wrapper = new EntityWrapper<>();
        wrapper.eq("name",genQo.getTableName());
        if (genTableService.selectCount(wrapper) > 0) {
            throw new GunsException(BizExceptionEnum.TABLE_EXIST);
        }
        model.addAttribute("tableInfo", tableService.getTableInfo(genQo));
        return PREFIX + "genTable_add.html";
    }

    /**
     * 跳转到修改业务表
     */
    @RequestMapping("/genTable_update/{genTableId}")
    public String genTableUpdate(@PathVariable Long genTableId, Model model) {
        GenTable genTable = genTableService.getGenTableAndColumn(genTableId);

        //获取数据库表结构
        GenQo genQo = new GenQo();
        genQo.setTableName(genTable.getName());
        TableInfo tableInfo = tableService.getTableInfo(genQo);

        //预防字段存在的情况
        if (genTable.getGenTableColumns() == null) {
            genTable.setGenTableColumns(new ArrayList<>());
        }

        //将数据库中新增的字段加入现有列表中
        for (TableField tableField : tableInfo.getFields()) {
            //字段是否存在标识符
            boolean isExist = false;

            for (GenTableColumn genTableColumn : genTable.getGenTableColumns()) {
                if (genTableColumn.getName().equals(tableField.getName())) {
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                GenTableColumn genTableColumn = new GenTableColumn();
                genTableColumn.setName(tableField.getName());
                genTableColumn.setComments(tableField.getComment());
                genTableColumn.setJavaField(tableField.getPropertyName());
                genTableColumn.setJavaType(tableField.getType());
                genTableColumn.setGenTableId(genTableId);
                genTable.getGenTableColumns().add(genTableColumn);
            }
        }

        model.addAttribute("tableInfo",genTable);
        LogObjectHolder.me().set(genTable);
        return PREFIX + "genTable_edit.html";
    }

    /**
     * 获取业务表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return genTableService.selectList(null);
    }

    /**
     * 新增业务表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(GenTable genTable) {
        genTableService.insert(genTable);
        return SUCCESS_TIP;
    }

    /**
     * 删除业务表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long genTableId) {
        genTableService.deleteById(genTableId);
        return SUCCESS_TIP;
    }

    /**
     * 修改业务表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(GenTable genTable) {
        genTableService.updateById(genTable);
        return SUCCESS_TIP;
    }

    /**
     * 业务表详情
     */
    @RequestMapping(value = "/detail/{genTableId}")
    @ResponseBody
    public Object detail(@PathVariable("genTableId") Integer genTableId) {
        return genTableService.selectById(genTableId);
    }
}
