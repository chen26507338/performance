package com.stylefeng.guns.modular.post.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.post.model.DeptPost;

/**
 * 机构职务配置服务类
 *
 * @author 
 * @Date 2021-02-18 15:45:15
 */
public interface IDeptPostService extends IService<DeptPost> {

    void importSetting(DeptPost deptPost);
}