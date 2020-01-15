package com.stylefeng.guns.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.common.persistence.model.Dict;

import java.util.List;

/**
 * <p>
  * 字典表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
public interface DictMapper extends BaseMapper<Dict> {
    /**
     * 根据字典名称查询字典列表
     * @param name
     * @return
     */
    List<Dict> getDictsByName(String name);
}