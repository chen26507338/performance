package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.generator.action.model.GenTable;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 业务表 服务类
 * </p>
 *
 * @author cp
 * @since 2018-01-13
 */
public interface IGenTableService extends IService<GenTable> {
    GenTable getGenTableAndColumn(Long genTableId);
}
