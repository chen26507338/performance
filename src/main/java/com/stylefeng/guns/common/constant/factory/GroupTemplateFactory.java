package com.stylefeng.guns.common.constant.factory;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.config.properties.BeetlProperties;
import com.stylefeng.guns.core.beetl.ShiroExt;
import com.stylefeng.guns.core.util.*;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.ClasspathResourceLoader;

import java.io.IOException;

public class GroupTemplateFactory {
    private static final byte[] LOCKER = new byte[0];
    private static GroupTemplate classpathResourceTemplate;


    public static GroupTemplate getClasspathResourceTemplate() throws IOException {
        if (classpathResourceTemplate == null) {
            synchronized (LOCKER) {
                if (classpathResourceTemplate == null) {
                    BeetlProperties properties = SpringContextHolder.getBean(BeetlProperties.class);
                    classpathResourceTemplate = new GroupTemplate(new ClasspathResourceLoader(), new Configuration(properties.getProperties()));
                    classpathResourceTemplate.registerFunctionPackage("shiro", new ShiroExt());
                    classpathResourceTemplate.registerFunctionPackage("tool", new ToolUtil());
                    classpathResourceTemplate.registerFunctionPackage("dateUtil", new DateUtils());
                    classpathResourceTemplate.registerFunctionPackage("kaptcha", new KaptchaUtil());
                    classpathResourceTemplate.registerFunctionPackage("constant", ConstantFactory.me());
                }
            }
        }
        return classpathResourceTemplate;
    }
}
