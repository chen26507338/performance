package com.stylefeng.guns.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.cache.Cache;
import com.stylefeng.guns.common.persistence.dao.RelationMapper;
import com.stylefeng.guns.common.persistence.dao.RoleMapper;
import com.stylefeng.guns.common.persistence.model.Relation;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.modular.system.dao.RoleDao;
import com.stylefeng.guns.modular.system.service.IRoleService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    RoleMapper roleMapper;

    @Resource
    RoleDao roleDao;

    @Resource
    RelationMapper relationMapper;

    @Override
    @Transactional(readOnly = false)
    public void setAuthority(Long roleId, String ids) {

        // 删除该角色所有的权限
        this.roleDao.deleteRolesById(roleId);

        // 添加新的权限
        for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", ids))) {
            Relation relation = new Relation();
            relation.setRoleid(roleId);
            relation.setMenuid(id);
            this.relationMapper.insert(relation);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void delRoleById(Long roleId) {
        //删除角色
        this.roleMapper.deleteById(roleId);

        // 删除该角色所有的权限
        this.roleDao.deleteRolesById(roleId);
    }

    @Override
    @Cacheable(value = Cache.CONSTANT,key = "'"+CACHE_TIPS+"'+#tips")
    public Role getByTips(String tips) {
        Role params = new Role();
        params.setTips(tips);
        return this.selectOne(new EntityWrapper<>(params));
    }

    @Override
    @Cacheable(value = Cache.CONSTANT,key = "'"+CACHE_ENTITY+"'+#id")
    public Role selectById(Serializable id) {
        return super.selectById(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict( value = Cache.CONSTANT,key = "'"+CACHE_ENTITY+"'+#entity.id"),
            @CacheEvict(value = Cache.CONSTANT,key = "'"+CACHE_LIST+"'")})
    public boolean updateById(Role entity) {
        return super.updateById(entity);
    }

}
