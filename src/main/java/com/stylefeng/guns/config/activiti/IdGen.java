package com.stylefeng.guns.config.activiti;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;

public class IdGen implements IdGenerator, SessionIdGenerator {
    @Override
    public String getNextId() {
        return IdWorker.get32UUID();
    }

    @Override
    public Serializable generateId(Session session) {
        return IdWorker.get32UUID();
    }
}
