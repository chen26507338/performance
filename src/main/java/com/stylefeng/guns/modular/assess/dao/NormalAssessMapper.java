package com.stylefeng.guns.modular.assess.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.modular.assess.model.NormalAssess;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 考核指标库Mapper 接口
 *
 * @author 
 * @Date 2020-02-02 13:18:03
 */
public interface NormalAssessMapper extends BaseMapper<NormalAssess> {
    List<NormalAssess> selectPage(RowBounds rowBounds, NormalAssess normalAssess);
}