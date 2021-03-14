package com.stylefeng.guns.modular.assess.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.modular.assess.model.AssessNormPoint;
import com.stylefeng.guns.modular.assess.model.SpecialAssessMember;
import com.stylefeng.guns.modular.assess.service.IAssessNormPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.assess.model.SpecialAssessMember;
import com.stylefeng.guns.modular.assess.dao.SpecialAssessMemberMapper;
import com.stylefeng.guns.modular.assess.service.ISpecialAssessMemberService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * 专项工作考核服务实现类
 *
 * @author
 * @Date 2021-02-25 18:46:34
 */
@Service
public class SpecialAssessMemberServiceImpl extends ServiceImpl<SpecialAssessMemberMapper, SpecialAssessMember> implements ISpecialAssessMemberService {

    @Autowired
    private IAssessNormPointService assessNormPointService;

    @Override
    @Transactional
    public boolean updateById(SpecialAssessMember entity) {
        SpecialAssessMember oldAssess = this.selectById(entity.getId());
        AssessNormPoint params = new AssessNormPoint();
        params.setUserId(oldAssess.getUserId());
        params.setYear(oldAssess.getYear());
        AssessNormPoint point = assessNormPointService.selectOne(new EntityWrapper<>(params));
        point.setZxgzMain(point.getZxgzMain() - oldAssess.getPoint());
        point.setZxgzMain(point.getZxgzMain() + entity.getPoint());
        point.updateById();
        return super.updateById(entity);
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        SpecialAssessMember oldAssess = this.selectById(id);
        AssessNormPoint params = new AssessNormPoint();
        params.setUserId(oldAssess.getUserId());
        params.setYear(oldAssess.getYear());
        AssessNormPoint point = assessNormPointService.selectOne(new EntityWrapper<>(params));
        point.setZxgzMain(point.getZxgzMain() - oldAssess.getPoint());
        point.updateById();
        return super.deleteById(id);
    }
}