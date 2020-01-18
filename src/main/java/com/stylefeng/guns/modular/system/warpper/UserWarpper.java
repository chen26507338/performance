package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.job.service.IJobService;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的包装类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:47:03
 */
public class UserWarpper extends BaseControllerWarpper {


    public UserWarpper(Object list) {
        super(list);

    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
//        map.put("sexName", ConstantFactory.me().getSexName((Integer) map.get("sex")));
//        map.put("roleName", ConstantFactory.me().getRoleName((String) map.get("roleid")));
//        map.put("deptName", ConstantFactory.me().getDeptName((Long) map.get("deptid")));
        map.put("statusName", ConstantFactory.me().getStatusName((Integer) map.get("status")));
    }

}
