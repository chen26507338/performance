package com.stylefeng.guns.base;

import com.alibaba.druid.util.Base64;
import com.stylefeng.guns.core.util.FileUtils;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NormalJunit {



    @Test
    public void httpStatus() {
        System.out.println(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void base64Pic() {
        try {
            System.out.println(Base64.byteArrayToBase64(FileUtils.readFileToByteArray(new File("C:\\Users\\Administrator\\Desktop\\TIM截图20180316085222.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPattern() {
        Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        Matcher m = p.matcher("1992-99-99");
        System.out.println(m.find());
    }

    @Test
    public void test() {
        System.out.println(6*1.2);
    }
}
