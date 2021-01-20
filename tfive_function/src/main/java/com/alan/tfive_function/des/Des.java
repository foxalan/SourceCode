package com.alan.tfive_function.des;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author alan
 * function: 加解密
 */
public class Des {

    private static final String TAG = "HomeActivity";
    private static final String type = "android";

    /**
     *
     * 经常使用加密算法:DES、3DES、RC4、AES，RSA等;
     *
     * 对称加密：des，3des，aes
     *
     * 非对称加密：rsa
     *
     * 不可逆加密：md5
     *
     * 加密模式:ECB、CBC、CFB、OFB等;
     *
     * 填充模式:NoPadding、PKCS1Padding、PKCS5Padding、PKCS7Padding
     *
     */

    public static void main(String[] args){
        //1.Base64加解密
        //setBase64();

        // Des
        setDES();
        // RSA

    }

    public static void setBase64(){
        String oldWord = "大家要注意身体，不要熬夜写代码";
        String encode = null;
        String decode = null;
        try {
            encode = Base64Util.encodeWord(oldWord);
            System.out.println("编码:"+encode);
            //解码

            decode = Base64Util.decodeWord(encode);
            System.out.println("编码:"+decode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void setDES() {

        /// ---------------------------------------- 静态 ------------------------------------------

        String key = "2012j214";        // 键值必须大于8位
        try {
            // 加密
            String s = DESUtil.desEncrypt("欧拉蕾", key);
            System.out.println("静态加密: " + s);

            // 解密
            String s1 = DESUtil.desDecrypt(s, key);
            System.out.println( "静态解密: " + s1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ----------------------------------------- 动态 ------------------------------------------

        // 动态生成秘钥
        String key2 = DESUtil.generateKey();
        try {

            // 动态加密
            String encode = DESUtil.encode(key2, "风里来，雨里去");
            System.out.println("动态加密: " + encode);

            // 动态解密
            String decode = DESUtil.decode(key2, encode);
            System.out.println("动态解密: " + decode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void setRSA(){
        // 获取到密钥对
        KeyPair keyPair = RSAUtil.generateRSAKeyPair(1024);

        // 获取公钥和私钥
        PublicKey aPublic = keyPair.getPublic();
        PrivateKey aPrivate = keyPair.getPrivate();
        ;
        byte[] aPublicEncoded = aPublic.getEncoded();
        byte[] aPrivateEncoded = aPrivate.getEncoded();

        try {
            // 公钥加密
            byte[] bytes = RSAUtil.encryptByPublicKey(type, "123".getBytes(), aPublicEncoded);
            String s1 = Base64Util.encodeWord(bytes.toString());
//            String encode = Base64Utils.encode(bytes);
//            Log.d(TAG, "公钥加密文件: " + encode);
            Log.d(TAG, s1);

            // 私钥解密
            byte[] bytes1 = RSAUtil.decryptByPrivateKey(type, bytes, aPrivateEncoded);
            String s = new String(bytes1);
            Log.d(TAG, "私钥解密文件: " + s);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("========================================");

//        try {
//            // 私钥加密
//            byte[] bytes = RSAUtil.encryptByPrivateKey(type, "456".getBytes(), aPrivateEncoded);
//            String encode = Base64Utils.encode(bytes);
//            Log.d(TAG, "私钥加密文件: " + encode);
//
//            // 公钥解密
//            byte[] bytes1 = RSAUtil.decryptByPublicKey(type, bytes, aPublicEncoded);
//            String s = new String(bytes1);
//            Log.d(TAG, "公钥解密文件: " + s);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}

