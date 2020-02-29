package com.clnn.pattern.security.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * RSA非对称加密
 */
public class RSAUtil {

    private static final Log logger = LogFactory.getLog(RSAUtil.class);

    public static final String RSA_PADDING_KEY = "RSA/ECB/PKCS1Padding";
    public static final String SIGNATURE_ALGORITHM_SHA1 = "SHA1WithRSA";

    /**
     * 获取RSA公钥
     * @return
     * @throws Exception
     */
    private static PublicKey getPublicKey(String path) throws Exception {
        InputStream is = null;
        try
        {
            //is = new FileInputStream("E:\\Key\\credoo_stg.cer");//本地测试
            is = RSAUtil.class.getResourceAsStream(path);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
            return cert.getPublicKey();
        }  catch (CertificateException e)
        {
            throw new Exception("E000030");
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * RSA公钥加密
     * @param plainText 待加密数据
     * @param s_publicKey 公钥字符串
     * @return
     */
    public static String encrypt(String plainText, String s_publicKey,String encode,String path) {
        if (plainText == null || s_publicKey == null) {
            return null;
        }
        try {

            PublicKey publicKey = getPublicKey(path);
            Cipher cipher = Cipher.getInstance(RSA_PADDING_KEY);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] enBytes = cipher.doFinal(plainText.getBytes(encode));
            String ret =  Base64Utils.encodeToString(enBytes);

            return formatString(ret);
        } catch (Exception e) {
            logger.error("RSA encrypt Exception:" + e);
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
    /**
     * 获取RSA私钥
     * @return
     * @throws Exception
     */
    private static PrivateKey getPrivateKey(String path, String storePassword, String storeAlias) throws Exception {
        char[] storePwdArr;
        int i;
        BufferedInputStream bis = null;
        try
        {
            KeyStore ks = KeyStore.getInstance("JKS");
            InputStream fis = RSAUtil.class.getResourceAsStream(path);
            bis = new BufferedInputStream(fis);
            //String storePassword = "qhzx_stg";//私钥密码，正式环境该值需要修改为生成时设置的值！！！测试环境无需再修改！！！
            //String storeAlias = "signKey";//私钥密码，正式环境该值需要修改为生成时设置的值！！！测试环境无需再修改！！！
            storePwdArr = new char[storePassword.length()];// store password
            for (i = 0; i < storePassword.length(); i++)
            {
                storePwdArr[i] = storePassword.charAt(i);
            }
            ks.load(bis, storePwdArr);
            PrivateKey priv = (PrivateKey) ks.getKey(storeAlias, storePwdArr);
            return priv;
        } catch (KeyStoreException e)
        {
            e.printStackTrace();
            throw new Exception("E000033");
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            throw new Exception("E000031", e);
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            throw new Exception("E000032", e);
        } catch (CertificateException e)
        {
            e.printStackTrace();
            throw new Exception("E000030", e);
        } catch (IOException e)
        {
            e.printStackTrace();
            throw new Exception("E000033", e);
        } catch (UnrecoverableKeyException e)
        {
            e.printStackTrace();
            throw new Exception("E000033", e);
        }
        finally
        {
            if (bis != null)
            {
                try
                {
                    bis.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * RSA私钥解密
     * @param enStr 待解密数据
     * @param s_privateKey 私钥字符串
     * @return
     */
    public static String decrypt(String enStr, String s_privateKey,String encode,String path,String storePassword,String storeAlias) {
        if (enStr == null || s_privateKey == null) {
            return null;
        }
        try {

            PrivateKey privateKey = getPrivateKey(path,storePassword,storeAlias);
            Cipher cipher = Cipher.getInstance(RSA_PADDING_KEY);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] sealByte =  Base64Utils.decodeFromString(enStr);
            byte[] deBytes = cipher.doFinal(sealByte);
            return new String(deBytes, encode);
        } catch (Exception e) {
            logger.error("RSA decrypt Exception:" + e);
        }
        return null;
    }
    /**
     * RSA签名
     *
     * SHA1摘要RSA签名
     * @param content 待签名数据
     * @param encode 字符集编码
     * @return
     */
    public static String signwithsha1(String content, String encode,String path,String storePassword,String storeAlias) {
        if (content == null|| encode == null) {
            return null;
        }
        try {
            PrivateKey priKey = getPrivateKey(path,storePassword,storeAlias);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_SHA1);
            signature.initSign(priKey);
            signature.update(content.getBytes(encode));
            byte[] signed = signature.sign();
            return Base64Utils.encodeToString(signed);
        } catch (Exception e) {
            logger.error("RSA sign Exception:" + e);
        }
        return null;
    }

    /**
     * RSA签名验证
     *
     * SHA1摘要RSA签名验证
     * @param content 待签名数据
     * @param sign 签名值
     * @param encode 字符集编码
     * @return 布尔值
     */
    public static boolean verifySignwithsha1(String content, String sign,
                                             String encode,String path) {
        if (content == null || sign == null|| encode == null) {
            return false;
        }
        try {
            PublicKey pubKey = getPublicKey(path);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_SHA1);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(encode));
            return signature.verify(Base64Utils.decodeFromString(sign));
        } catch (Exception e) {
            logger.error("RSA verifySign Exception:" + e);
        }
        return false;
    }

}
