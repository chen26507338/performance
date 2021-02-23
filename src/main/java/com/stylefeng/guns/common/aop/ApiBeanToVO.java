package com.stylefeng.guns.common.aop;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.druid.util.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.stylefeng.guns.core.base.BaseModel;
import com.stylefeng.guns.core.base.TokenConfig;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.AESUtil;
import com.stylefeng.guns.core.util.DateUtils;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;


/*
    接口返回结果 实体bean转业务bean
 */
@Aspect
@Component
@Data
@ConfigurationProperties(prefix = "response-bean")
public class ApiBeanToVO {

    private Logger logger = LoggerFactory.getLogger(ApiBeanToVO.class);

    private Boolean encryption;

    @Autowired
    FastJsonHttpMessageConverter4 converter4;

    private void convert(Object item, Object temp) {
        String lang = HttpKit.getRequest().getParameter("lang");
        String timezone = HttpKit.getRequest().getParameter("timezone");

        if (StrUtil.isNotBlank(lang) && lang.equalsIgnoreCase("zh_hk")) {
            lang = "TC";
        }
        Field[] files = ReflectUtil.getFields(temp.getClass());
        for (Field file : files) {
            Object fileValue = null;
            if (item instanceof Map) {
                Map itemMap = (Map) item;
                if (StrUtil.isNotBlank(lang) && !lang.equals("ZH")) {
                    fileValue = itemMap.get(file.getName() + lang);

                    //无值默认选择英语
                    if (ToolUtil.isEmpty(fileValue)) {
                        fileValue = itemMap.get(file.getName() + "EN");
                    }
                }

                if (ToolUtil.isEmpty(fileValue)) {
                    fileValue = itemMap.get(file.getName());
                }
            } else {
                if (StrUtil.isNotBlank(lang) && !lang.equals("ZH")) {
                    fileValue = ReflectUtil.getFieldValue(item, file.getName() + lang);
                    //无值默认选择英语
                    if (ToolUtil.isEmpty(fileValue)) {
                        fileValue = ReflectUtil.getFieldValue(item,file.getName() + "EN");
                    }
                }
                if (ToolUtil.isEmpty(fileValue)) {
                    fileValue = ReflectUtil.getFieldValue(item,file.getName());
                }
            }
            if (fileValue != null && !Modifier.isFinal(file.getModifiers())) {
                //时区转换
                if (StrUtil.isNotBlank(timezone) && file.getType() == Date.class) {
                    SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sdt.setTimeZone(TimeZone.getTimeZone(ZoneId.ofOffset("GMT", ZoneOffset.of(timezone))));
                    String newTime = sdt.format(fileValue);
                    fileValue = DateUtil.parse(newTime);
                }
                ReflectUtil.setFieldValue(temp, file.getName(), fileValue);
            }
        }

        //扩展参数赋值
        try {
            if (item instanceof BaseModel) {
                BaseModel tempBean = (BaseModel) item;
                if (!BeanUtil.isEmpty(tempBean.getExpand())) {
                    for (Object o : tempBean.getExpand().entrySet()) {
                        Map.Entry<String, Object> entry = (Map.Entry<String, Object>) o;
                        if (ReflectUtil.hasField(temp.getClass(), entry.getKey())) {
                            ReflectUtil.setFieldValue(temp, entry.getKey(), entry.getValue());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.info("转换异常：", e);
        }

    }

    @AfterReturning(value = "@annotation(apiOperation)",returning = "result")
    public void doAfter(ApiOperation apiOperation,Tip result) {
        if (apiOperation.response() != Void.class && result.getResBody() != null && result.getResBody().getClass() != apiOperation.response()) {
            try {
                Object resBody;
                if (result.getResBody() instanceof List) {
                    List list = new ArrayList();
                    for (Object item : (List) result.getResBody()) {
                        Object temp = apiOperation.response().newInstance();
                        convert(item,temp);
                        list.add(temp);
                    }
                    resBody = list;
                }else {
                    resBody = apiOperation.response().newInstance();
                    convert(result.getResBody(),resBody);
                }
                result.setResBody(resBody);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (this.getEncryption() != null && this.getEncryption() && result.getResBody() != null) {
            FastJsonConfig fastJsonConfig = converter4.getFastJsonConfig();
            String resBody = JSON.toJSONString(result.getResBody(), fastJsonConfig.getSerializeConfig(), fastJsonConfig.getSerializeFilters(),SerializerFeature.WriteDateUseDateFormat,SerializerFeature.PrettyFormat,
                    SerializerFeature.WriteMapNullValue);

            TokenConfig tokenConfig = SpringContextHolder.getBean(TokenConfig.class);
            String gzip = HttpKit.getRequest().getParameter("gzip");
            String res;
            if (StrUtil.isNotBlank(gzip) && "gzip".equals(gzip)) {
//                res = AESUtil.encrypt(Base64.byteArrayToBase64(ZipUtil.gzip(Base64.byteArrayToBase64(resBody.getBytes(StandardCharsets.UTF_8)).getBytes())), tokenConfig.getKey(), tokenConfig.getIv());
                res = AESUtil.encrypt(Base64.byteArrayToBase64(ZipUtil.gzip(resBody.getBytes(StandardCharsets.UTF_8))), tokenConfig.getKey(), tokenConfig.getIv());
            } else {
                res = AESUtil.encrypt(Base64.byteArrayToBase64(resBody.getBytes(StandardCharsets.UTF_8)), tokenConfig.getKey(), tokenConfig.getIv());
            }
            result.setResBody(res);
        }
    }
}
