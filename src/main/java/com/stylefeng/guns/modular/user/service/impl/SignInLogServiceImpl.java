package com.stylefeng.guns.modular.user.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.user.model.SignInLog;
import com.stylefeng.guns.modular.user.dao.SignInLogMapper;
import com.stylefeng.guns.modular.user.service.ISignInLogService;

/**
 * 打卡签到管理服务实现类
 *
 * @author 
 * @Date 2021-03-10 19:38:41
 */
 @Service
public class SignInLogServiceImpl extends ServiceImpl<SignInLogMapper, SignInLog> implements ISignInLogService {

}