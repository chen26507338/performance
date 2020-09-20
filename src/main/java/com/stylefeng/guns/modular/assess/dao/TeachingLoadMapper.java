package com.stylefeng.guns.modular.assess.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.modular.assess.model.TeachingLoad;

import java.util.List;

/**
 * 教学工作量Mapper 接口
 *
 * @author 
 * @Date 2020-09-20 09:20:09
 */
public interface TeachingLoadMapper extends BaseMapper<TeachingLoad> {
    List<TeachingLoad> selectGroup(TeachingLoad teachingLoad);
}