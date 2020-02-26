package com.stylefeng.guns.modular.assess.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.modular.assess.model.AssessNormPoint;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 考核分Mapper 接口
 *
 * @author 
 * @Date 2020-02-09 10:15:21
 */
public interface AssessNormPointMapper extends BaseMapper<AssessNormPoint> {
    List<AssessNormPoint> selectPage(RowBounds rowBounds, AssessNormPoint assessNormPoint);
}