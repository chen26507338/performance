package com.stylefeng.guns.gen;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.stylefeng.guns.base.BaseJunit;
import com.stylefeng.guns.generator.action.model.GenQo;
import com.stylefeng.guns.modular.code.service.TableService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * Created by cp on 2018/1/14.
 */
public class GenTest extends BaseJunit{

    @Resource
    private TableService tableService;

    @Test
    public void getTableInfo() {
        GenQo genQo = new GenQo();
        genQo.setTableName("sys_dept");
        TableInfo tableInfo = tableService.getTableInfo(genQo);
        System.out.println(tableInfo.getName());
    }
}
