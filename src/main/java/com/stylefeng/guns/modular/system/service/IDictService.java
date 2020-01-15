package com.stylefeng.guns.modular.system.service;

/**
 * 字典服务
 *
 * @author fengshuonan
 * @date 2017-04-27 17:00
 */
public interface IDictService {

    /**
     * 通用状态：正常
     */
    int NORMAL_STATUS_NORMAL = 1;
    /**
     * 通用状态：禁止
     */
    int NORMAL_STATUS_DISABLED = 0;

    /**
     * 添加字典
     *
     * @author fengshuonan
     * @Date 2017/4/27 17:01
     */
    void addDict(String dictName, String dictValues);

    /**
     * 编辑字典
     *
     * @author fengshuonan
     * @Date 2017/4/28 11:01
     */
    void editDict(Long dictId, String dictName, String dicts);

    /**
     * 删除字典
     *
     * @author fengshuonan
     * @Date 2017/4/28 11:39
     */
    void delteDict(Long dictId);

}
