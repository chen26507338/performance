package com.stylefeng.guns.modular.system.model;

import cn.hutool.core.util.StrUtil;

/**
 * 是否是菜单的枚举
 *
 * @author fengshuonan
 * @date 2017年6月1日22:50:11
 */
public enum DeptAssess {

    JXDD(1298444468562886657L, "jxdd"),
    LDJL(1224197239301328897L, "ldjl"),
    SDSF(1308301760170954753L, "sdsf"),
    DFDJ(1308301836809277442L, "dfdj"),
    ZZJL(1224197239301328897L, "zzjl"),
    YSXT(1308301836809277442L, "ysxt"),
    ZZPA(1308301643695132673L, "zzpa");

    long deptId;
    String assessType;

    DeptAssess(long deptId, String assessType) {
        this.deptId = deptId;
        this.assessType = assessType;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getAssessType() {
        return assessType;
    }

    public void setAssessType(String assessType) {
        this.assessType = assessType;
    }

    public static Long typeOf(String assessType) {
        if (StrUtil.isBlank(assessType)) {
            return null;
        } else {
            for (DeptAssess s : DeptAssess.values()) {
                if (s.getAssessType().equals(assessType)) {
                    return s.getDeptId();
                }
            }
            return null;
        }
    }
}
