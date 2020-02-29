package com.clnn.security;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


@Slf4j
@Component
@Data
public class SecureKmsUtils {

    private static final String ENCODING = "UTF-8";

    private static final char[] bcdLookup = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static final String KEY_ALGORITHM = "RSA";

    public static final String SIGNATURE_ALGORITHM_SHA1 = "SHA1WithRSA";


    /**
     * AES解密.
     *
     * @param content 待解密内容.
     * @param aesKey 解密密码.
     * @return
     */
    public static String decryptAES(String content, String aesKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random =SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(bytesToHexStr(Base64Utils.decode(aesKey.getBytes())).getBytes());
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodedFormat = secretKey.getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(enCodedFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] contents = Base64.decodeBase64(content);
            byte[] result = cipher.doFinal(contents);
            return new String(result, ENCODING);
        } catch (Exception e) {
            log.error("AESUtils 解密失败：{}",e);
        }
        return null;
    }

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param aesKey 加密密码
     * @return
     */
    public static String encryptAES(String content, String aesKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random =SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(bytesToHexStr(Base64Utils.decode(aesKey.getBytes())).getBytes());
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            byte[] byteContent = content.getBytes(ENCODING);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(byteContent);
            return Base64.encodeBase64String(result);
        } catch (Exception e) {
            log.error("AESUtils 加密失败：{}",e);
        }
        return null;
    }


    /**
     * Transform the specified byte into a Hex String form.
     */
    public static final String bytesToHexStr(byte[] bcd) {
        StringBuffer s = new StringBuffer(bcd.length * 2);
        for (int i = 0; i < bcd.length; i++) {
            s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
            s.append(bcdLookup[bcd[i] & 0x0f]);
        }
        return s.toString();
    }


    /**
     * RSA签名
     * <p>
     * SHA1摘要RSA签名
     *
     * @param content    待签名数据
     * @param privateKey 关联方私钥
     * @return
     */
    public static String sign(String content, String privateKey) {
        if (content == null || privateKey == null) {
            return null;
        }
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes(ENCODING)));
            KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_SHA1);
            signature.initSign(priKey);
            signature.update(content.getBytes(ENCODING));
            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed), ENCODING);
        } catch (Exception e) {
            log.error("signwithsha1 fail! e:{}",e);
        }
        return null;
    }

    /**
     * RSA签名验证
     * <p>
     * SHA1摘要RSA签名验证
     *
     * @param content   待签名数据
     * @param sign      签名值
     * @param publicKey 分配给关联方公钥
     * @return 布尔值
     */
    public static boolean verifySign(String content, String sign, String publicKey) {
        if (content == null || sign == null || publicKey == null) {
            return false;
        }
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            byte[] encodedKey = Base64.decodeBase64(publicKey.getBytes(ENCODING));
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_SHA1);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(ENCODING));
            return signature.verify(Base64.decodeBase64(sign.getBytes(ENCODING)));
        } catch (Exception e) {
            log.error("verifySignwithsha1 fail! e:{}",e);
        }
        return false;
    }


    public static void main(String[] args) {
        String data = "111111";
        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCaK1RSf4Ql1RtUTQGLz83B2WQY\n" +
                "L8HS5NS87A00O8sEwjOdYvInCB7c49JMW7l0yNSOs9p/4Sb712UuYanIOP3TqJc2LgBiC0Q5iY3z\n" +
                "qC3/AhW90hXB/uOo2A343YPMcBjdqXhLZfF4bmAvx0OjuYfXxIU05xGRwchQFA0A34GBAuNx+hwe\n" +
                "1s/FFC0KhIJjyYOfZ0ZEyYSGBipfl8xtBYTUrAVBbE3ntpk+NUvHeOLNH41PtEqsmKu6EovIIumS\n" +
                "Hpmvi2TCU7pVEtbB6qoehwB6PwSue6NTCISHXoSqK1wUm5SjLvLjihM2byJbbGE3eHku2UeCipHK\n" +
                "mkEmqJXDcc1/AgMBAAECggEAGiif9h+M3J58NKGcUYzA7NNmGHSB/ra1IhkrZGGA4R3ShDH1xIms\n" +
                "Mu61EB/AKACrkEsUF/6sDq9Ni8EmfcKtzuwcPCUyC3C98sVUQLOYmlxQNBUhe/kzx8JhHIj5hAbB\n" +
                "MVs//gseHcEcsP6ui2k/k2zU7VUHtiObSXsdebhy0ODZGyTtOtX5W59HI/UBsEhTsCCdQVPAic4G\n" +
                "4kPV9j94GPoXRWBtFp7uPIftt8dY/+Xay1lu73zS9LjQoNENDA/RLhs/8LTbsIxnWSKbf/N/ZpLP\n" +
                "EVmXrIfBL5yoRg56RduR57XQI/IjTS6cZL2T7hU1iXxtmxUI1wVHmU8k4Oc9AQKBgQDPm5Iul8+N\n" +
                "lWW1LsM5gi0XZnavbAFR8JY55p+6biWLi4OhAp0vVXDI8e+UtMOomeXuJw05U/HH/tWIVIQ9Cfd5\n" +
                "uxhUHbcp7MZHWybhWVzlafWFytG58hkgCbH2+GQN9JnFb/jStCrILUOWq0yi1FKxnlPYmMnGqM1q\n" +
                "6Zq9XFslyQKBgQC+GvanLdClmk2Hn29hzphHj8UvTA0JBFec2fck6tv1xraRMhutsgrEPz0B2ppR\n" +
                "+coioV9fOunfRpH6Y74IUPklKNYx4x6sRvYvgQ5zn53H9D3ydDlbywgfzyDE4j6kN9CkBzNd16dS\n" +
                "axseNlcqC0VfuKAowSyg3PZ93jvuIkwdBwKBgQCZnnYmv/K2w2uFC9stfKO9Avaji3nWlf2zTnG2\n" +
                "07Y4cb2usG57De/s4Qj0smVtJqO6+26Pkwv/fxOmU1OejvduPgEcjPhxr2BwgIRej29r2mHw5EkY\n" +
                "HIp26HWlV9tBcFMwg1BNKT/W65dtr8d/XTjrcUp9pWD4/QMzLzRvFh3j8QKBgD+Jo4acuW+f8umx\n" +
                "T/jQo/THCJCWSoUI/n9eGkwscvfiQoKkzLtxFkghGrLT7WAYrIaGiOm7Nw949rFJROpipeLVk0F5\n" +
                "N3c9AGtAJyRd5M4/w7SVQ9Z6NWzsPXeQZn9caE9qWLC0syfIxHeEkf1rZvRnMKBucAwTzg6jiEQ/\n" +
                "ElFhAoGBAMdP5kPwWE5ctJ4JujTzvyZ2EB8P+w8tC7XeZX2Ox9fTzd8UB4HL78sZXFOZLTaJnjoA\n" +
                "hXoq+MUagj2+cLSSFrGqE1KWlmL41kQaTGZVE8KHhix1tf3CQvvhL+4hFiP+NFe7oZgZaEPbsiT8\n" +
                "7noXUUqy2aiCP0wVDlcOJIBSPdVc";
        String sign = sign(data, privateKey);
        System.out.println(sign);
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmitUUn+EJdUbVE0Bi8/NwdlkGC/B0uTU\n" +
                "vOwNNDvLBMIznWLyJwge3OPSTFu5dMjUjrPaf+Em+9dlLmGpyDj906iXNi4AYgtEOYmN86gt/wIV\n" +
                "vdIVwf7jqNgN+N2DzHAY3al4S2XxeG5gL8dDo7mH18SFNOcRkcHIUBQNAN+BgQLjcfocHtbPxRQt\n" +
                "CoSCY8mDn2dGRMmEhgYqX5fMbQWE1KwFQWxN57aZPjVLx3jizR+NT7RKrJiruhKLyCLpkh6Zr4tk\n" +
                "wlO6VRLWweqqHocAej8ErnujUwiEh16EqitcFJuUoy7y44oTNm8iW2xhN3h5LtlHgoqRyppBJqiV\n" +
                "w3HNfwIDAQAB";
        boolean success = verifySign(data, sign, publicKey);
        System.out.println(success);
        String aesKey = "zIysTZZtUnmCfYgX9MLslVbwt72srFQt2fo7uGNdZHw=";
        String encrypt = encryptAES(data, aesKey);
        System.out.println(encrypt);
        String decrypt = decryptAES(encrypt, aesKey);
        System.out.println(decrypt);
    }

}
