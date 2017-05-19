package com.zhaopin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guojun.wang on 2017/5/11.
 */
public class StrCryptoTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDecrypt() throws Exception {
        com.zhaopin.StrCrypto crypto = new com.zhaopin.StrCrypto("abcdef", "123456");
        String str = crypto.encrypt(12007036, "JR394077410R90250003000");
        System.out.println(str);
        assertTrue(str.length() == 22);
    }
}