package com.clnn.pattern.security.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Base64Utils;


import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 3DES加密
 */
public class TripleDESUtil {
    private static final Log logger = LogFactory.getLog(TripleDESUtil.class);

    public static String decrypt(String key,String text) throws  Exception {
        try {
            text = formatString(text);
            //DESedeKeySpec会帮你生成24位秘钥，key可以是任意长度
            Cipher cipher = generateCipher(key, Cipher.DECRYPT_MODE);
            Base64Utils.decodeFromString(text);
            byte[] res = cipher.doFinal(Base64Utils.decodeFromString(text));
            //encodeBase64会对字符串3位一组自动补全，因而最后可能会出现 == 或者 =
            return new String(res);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private static Cipher generateCipher(String key, int decryptMode) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes("utf-8"));
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = factory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(decryptMode, secretKey);
        return cipher;
    }

    /**
     * @param key 秘钥,由提供方统一分配
     * @param text 需要加密的数据
     * @return
     * @throws Exception
     * 简单了解下 ： DES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法。DES加密算法出自IBM的研究，
     * 后来被美国政府正式采用，之后开始广泛流传，但是近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，较为容易破解。
     */
    public static String encrypt(String key,String text) throws  Exception {
        try {
            text = formatString(text);
            byte[] src = text.getBytes("utf-8");
            //DESedeKeySpec会帮你生成24位秘钥，key可以是任意长度
            Cipher cipher = generateCipher(key, Cipher.ENCRYPT_MODE);
            byte[] res = cipher.doFinal(src);
            //encodeBase64会对字符串3位一组自动补全，因而最后可能会出现 == 或者 =
            return new String(Base64Utils.encode(res));

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    /**
     * 格式化RSA加密字符串,去掉换行和渐近符号
     * @param sourceStr
     * @return
     */
    private static String formatString(String sourceStr) {
        if (sourceStr == null) {
            return null;
        }
        return sourceStr.replaceAll("\\r", "").replaceAll("\\n", "");
    }
}
