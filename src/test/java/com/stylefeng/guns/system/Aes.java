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

    @Test
    public void test() {
        String sql = "INSERT INTO game_bili (GAME_TYPE, BILI_TYPE, BILI_NAME, BILI, RESULT, AREA_ID) VALUE";
        String value = ",(3,4,'{}',8,'{}',7)";
        System.out.println(sql);
        for (int i = 0; i < 10; i++) {
            for (int i1 = 1; i1 < 11; i1++) {
                System.out.println(ToolUtil.format(value, i1, i));
            }
        }

    }

    @Test
    public void doubleFace() {
        String sql =
                "INSERT INTO game_bili (GAME_TYPE, BILI_TYPE, BILI_NAME, BILI, RESULT, AREA_ID) VALUE (3,5,'大',1.8,'{}',7);\n" +
                        "INSERT INTO game_bili (GAME_TYPE, BILI_TYPE, BILI_NAME, BILI, RESULT, AREA_ID) VALUE (3,5,'小',1.8,'{}',7);\n" +
                        "INSERT INTO game_bili (GAME_TYPE, BILI_TYPE, BILI_NAME, BILI, RESULT, AREA_ID) VALUE (3,5,'单',1.8,'{}',7);\n" +
                        "INSERT INTO game_bili (GAME_TYPE, BILI_TYPE, BILI_NAME, BILI, RESULT, AREA_ID) VALUE (3,5,'双',1.8,'{}',7);";
        String sqlLong =
                "INSERT INTO game_bili (GAME_TYPE, BILI_TYPE, BILI_NAME, BILI, RESULT, AREA_ID) VALUE (3,5,'龙',1.8,'{}',7);\n" +
                        "INSERT INTO game_bili (GAME_TYPE, BILI_TYPE, BILI_NAME, BILI, RESULT, AREA_ID) VALUE (3,5,'虎',1.8,'{}',7);";

        for (int i = 0; i < 10; i++) {
            System.out.println(ToolUtil.format(sql, i, i, i, i));
            if (i < 5) {
                System.out.println(ToolUtil.format(sqlLong, i, i));
            }
        }

    }

    @Test
    public void firstAndOne() {
        String sql = "INSERT INTO game_bili (GAME_TYPE, BILI_TYPE, BILI_NAME, BILI, RESULT, AREA_ID) VALUE (3,6,'{}',,'10',7);";

        for (int i = 3; i < 20; i++) {
            System.out.println(ToolUtil.format(sql, i));
        }
    }

    @Test
    public void testNetWork() throws IOException {
        Map<String, String> param = new HashMap<>();
        param.put("account", "123");
        param.put("password", "123");
//        Response response = OkHttpUtils.getInstance().post("http://47.91.253.140:8088/api/v1/login", param);
        Response response = OkHttpUtils.getInstance().post("http://localhost:8080/api/v1/login", param);
        System.out.println(response.code());
    }


}
