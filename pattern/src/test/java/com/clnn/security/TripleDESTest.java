package com.clnn.security;

import com.clnn.pattern.security.util.TripleDESUtil;
import org.junit.Test;

public class TripleDESTest {

    private String key = "SK803@!QLF-D25WEDA5E52DA";
    private String text = "hello world";
    @Test
    public void testDes() throws Exception{
        String enStr = TripleDESUtil.encrypt(key,text);
        System.out.println(enStr);
        String deStr = TripleDESUtil.decrypt(key,enStr);
        System.out.println(deStr);
    }
}
