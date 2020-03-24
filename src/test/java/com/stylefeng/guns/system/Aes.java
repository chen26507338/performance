package com.stylefeng.guns.system;

import com.stylefeng.guns.core.util.AESUtil;
import com.stylefeng.guns.core.util.OkHttpUtils;
import com.stylefeng.guns.core.util.ToolUtil;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Aes {
    @Test
    public void encrypt() {
        String key = "0cVsLXUTbSp6eKkv";
        String iv = "o7TM7pFRlMv1wWW2";

        String s = "c664e62f41bb4e9db59b77819692f9ea" + System.currentTimeMillis();
        String s1 = AESUtil.encrypt(s, key, iv);
        System.out.println("s1:" + s1);

    }
}
