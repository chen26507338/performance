package com.stylefeng.guns.modular.code.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.mapper.SqlRunner;
//import com.stylefeng.guns.core.datasource.DruidProperties;
import com.stylefeng.guns.generator.action.config.WebGeneratorConfig;
import com.stylefeng.guns.generator.action.model.GenQo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 获取数据库所有的表
 *
 * @author fengshuonan
 * @date 2017-12-04-下午1:37
 */
@Service
public class TableService {

    @Value("${spring.datasource.db-name}")
    private String dbName;

//    @Autowired
//    private DruidProperties druidProperties;

    @Resource
    private DruidDataSource druidDataSource;
    /**
     * 获取当前数据库所有的表信息
     */
    public List<Map<String, Object>> getAllTables() {
        String sql = "select TABLE_NAME as tableName,TABLE_COMMENT as tableComment from information_schema.`TABLES` where TABLE_SCHEMA = '" + dbName + "'";
        return SqlRunner.db().selectList(sql);
    }

    public TableInfo getTableInfo(GenQo genQo) {
        genQo.setUrl(druidDataSource.getUrl());
        genQo.setUserName(druidDataSource.getUsername());
        genQo.setPassword(druidDataSource.getPassword());
        WebGeneratorConfig webGeneratorConfig = new WebGeneratorConfig(genQo);
        return webGeneratorConfig.getTableInfo();
    }
}
