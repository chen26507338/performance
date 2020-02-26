package com.stylefeng.guns.modular.assess.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.model.AssessNormPoint;
import com.stylefeng.guns.modular.assess.dao.AssessNormPointMapper;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;

/**
 * 考核分服务实现类
 *
 * @author 
 * @Date 2020-02-09 10:15:21
 */
 @Service
public class AssessNormPointServiceImpl extends ServiceImpl<AssessNormPointMapper, AssessNormPoint> implements IAssessNormPointService {
    @Override
    public Page<AssessNormPoint> selectPage(Page<AssessNormPoint> page, Wrapper<AssessNormPoint> wrapper) {
        page.setRecords(this.baseMapper.selectPage(page, wrapper.getEntity()));
        return page;
    }
}