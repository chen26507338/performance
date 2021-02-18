package com.stylefeng.guns.modular.post.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.stylefeng.guns.common.constant.state.YesNo;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.modular.job.model.Dept;
import com.stylefeng.guns.modular.job.service.IDeptService;
import com.stylefeng.guns.modular.pay.model.PaySetting;
import com.stylefeng.guns.modular.post.model.PostSetting;
import com.stylefeng.guns.modular.post.service.IPostSettingService;
import com.stylefeng.guns.modular.system.service.IUserService;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.post.model.DeptPost;
import com.stylefeng.guns.modular.post.dao.DeptPostMapper;
import com.stylefeng.guns.modular.post.service.IDeptPostService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 机构职务配置服务实现类
 *
 * @author
 * @Date 2021-02-18 15:45:15
 */
@Service
public class DeptPostServiceImpl extends ServiceImpl<DeptPostMapper, DeptPost> implements IDeptPostService {
    @Resource
    private GunsProperties gunsProperties;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IPostSettingService postSettingService;

    @Override
    @Transactional
    public void importSetting(DeptPost deptPost) {
        if (deptPost.getExpand().get("fileName") == null) {
            throw new GunsException("请上传导入文件");
        }

        ExcelReader reader = ExcelUtil.getReader(gunsProperties.getFileUploadPath() + deptPost.getExpand().get("fileName"));
        reader.addHeaderAlias("部门", "dept");
        reader.addHeaderAlias("职工编号", "account");
        reader.addHeaderAlias("领导或内设科室", "ldks");
        reader.addHeaderAlias("职级", "zj");
        reader.addHeaderAlias("职务", "zw");
        reader.addHeaderAlias("计入定编数", "isDb");
        reader.addHeaderAlias("是否星号", "isStar");
        List<Map> users = reader.readAll(Map.class);
        for (Map map : users) {
            String deptName = map.get("dept") + "";
            Dept dept = deptService.getByName(deptName);
            if (dept == null) {
                throw new GunsException(StrUtil.format("部门 {} 不存在", deptName));
            }

            PostSetting postSParam = new PostSetting();
            postSParam.setLdks(map.get("ldks") + "");
            postSParam.setZj(map.get("zj") + "");
            postSParam.setZw((map.get("zw") + "").replace("*", ""));
            PostSetting postSetting = postSettingService.getByCache(postSParam);
            if (postSetting == null) {
                postSettingService.insert(postSParam);
                postSetting.setId(postSParam.getId());
            }

            User user = userService.getByAccount(map.get("account") + "");

            DeptPost dPost = new DeptPost();
            dPost.setDeptId(dept.getId());
            dPost.setPostId(postSetting.getId());
            dPost.setUserId(user.getId());
            dPost.setIsDb(map.get("isDb") == null ? YesNo.NO.getCode() : YesNo.YES.getCode());
            dPost.setIsStar(map.get("isStar") == null ? YesNo.NO.getCode() : YesNo.YES.getCode());
            dPost.insert();
        }
    }
}