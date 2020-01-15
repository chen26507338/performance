package com.stylefeng.guns.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.persistence.dao.GenTableColumnMapper;
import com.stylefeng.guns.generator.action.model.GenTable;
import com.stylefeng.guns.common.persistence.dao.GenTableMapper;
import com.stylefeng.guns.generator.action.model.GenTableColumn;
import com.stylefeng.guns.modular.system.service.IGenTableService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 * 业务表 服务实现类
 * </p>
 *
 * @author cp
 * @since 2018-01-13
 */
@Service
public class GenTableServiceImpl extends ServiceImpl<GenTableMapper, GenTable> implements IGenTableService {
    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    @Override
    @Transactional
    public boolean insert(GenTable entity) {
        super.insert(entity);
        if (entity.getGenTableColumns() != null && !entity.getGenTableColumns().isEmpty()) {
            for (GenTableColumn genTableColumn : entity.getGenTableColumns()) {
                genTableColumn.setGenTableId(entity.getId());
                genTableColumnMapper.insert(genTableColumn);
            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        EntityWrapper<GenTableColumn> wrapper = new EntityWrapper<>();
        wrapper.eq("gen_table_id", id);
        genTableColumnMapper.delete(wrapper);
        return super.deleteById(id);
    }

    @Override
    @Transactional
    public boolean updateById(GenTable entity) {
        super.updateById(entity);
        if (entity.getGenTableColumns() != null && !entity.getGenTableColumns().isEmpty()) {
            for (GenTableColumn genTableColumn : entity.getGenTableColumns()) {
                if (genTableColumn.getId() == null) {
                    genTableColumnMapper.insert(genTableColumn);
                } else {
                    genTableColumnMapper.updateAllColumnById(genTableColumn);
                }
            }
        }
        return true;
    }

    @Override
    public GenTable getGenTableAndColumn(Long genTableId) {
        GenTable genTable = this.selectById(genTableId);
        Wrapper<GenTableColumn> genTableColumnWrapper = new EntityWrapper<>();
        genTableColumnWrapper.eq("gen_table_id", genTable.getId());
        genTable.setGenTableColumns(genTableColumnMapper.selectList(genTableColumnWrapper));

        return genTable;
    }
}
