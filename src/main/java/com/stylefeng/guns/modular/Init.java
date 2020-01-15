package com.stylefeng.guns.modular;

import com.stylefeng.guns.core.util.SpringContextHolder;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Init {
    @PostConstruct
    public void init() {
        Flyway flyway = SpringContextHolder.getBean(Flyway.class);
        flyway.migrate();
    }
}
