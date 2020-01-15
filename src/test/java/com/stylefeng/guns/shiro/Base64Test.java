package com.stylefeng.guns.shiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.OperationMode;
import org.apache.shiro.crypto.PaddingScheme;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Arrays;

public class Base64Test {

    /**
     * Shiro 记住密码采用的是AES加密，AES key length 需要是16位，该方法生成16位的key
     */
    public static void main(String[] args) {
    	
        String keyStr = "guns";
        
        byte[] keys;
		try {
			keys = keyStr.getBytes("UTF-8");
	        System.out.println(Base64Utils.encodeToString(Arrays.copyOf(keys, 16)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
    }

    @Test
    public void test() {
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setMode(OperationMode.CBC);
        aesCipherService.setPaddingScheme(PaddingScheme.PKCS5);
        Key key = aesCipherService.generateNewKey(128);
//        aesCipherService.encrypt()
        System.out.println(key.getAlgorithm());
        System.out.println(key.getFormat());
    }

    @Test
    public void random() {
        int a = (int) (Math.random() * Math.pow(10,6));
        System.out.println(a);
    }
}
