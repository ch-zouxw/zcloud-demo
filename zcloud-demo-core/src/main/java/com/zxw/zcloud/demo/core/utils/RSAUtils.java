package com.zxw.zcloud.demo.core.utils;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


/**
 * @author Mr.Zheng
 * @date 2014年8月22日 下午1:44:23
 */
public final class RSAUtils {
    private static final String RSA = "RSA";
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    private static int keylength = 1024;
    private static String CHARSET = "utf-8";

    /*************************** 加密、加签、验签、解密 方法begin ******************************/

    /**
     * 用公钥加密
     */
    public static String encryptData(String content, String pubKey)
            throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(pubKey.getBytes());
        PublicKey publicKey = getPublicKey(publicKeyBytes);
        byte[] data = encryptData(content.getBytes(CHARSET), publicKey);
        return new String(Base64.getEncoder().encode(data), CHARSET);
    }

    /**
     * 用私钥解密
     */
    public static String decryptData(String content, String priKey)
            throws Exception {
        byte[] priKeyBytes = Base64.getDecoder().decode(priKey.getBytes());
        PrivateKey privateKey = getPrivateKey(priKeyBytes);
        byte[] data = decryptData(Base64.getDecoder().decode(content), privateKey);
        return new String(data, CHARSET);
    }

    /**
     * 用私钥签名
     */
    public static String doSign(String content, String priKey)
            throws Exception {
        byte[] priKeyBytes = Base64.getDecoder().decode(priKey.getBytes());
        PrivateKey privateKey = getPrivateKey(priKeyBytes);
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initSign(privateKey);
        signature.update(content.getBytes(CHARSET));
        byte[] signed = signature.sign();
        return new String(Base64.getEncoder().encode(signed), CHARSET);
    }

    /**
     * 用公钥验签
     */
    public static boolean verifySign(String content, String sign, String pubKey)
            throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(pubKey.getBytes());
        PublicKey publicKey = getPublicKey(publicKeyBytes);
        return doCheck(content.getBytes(CHARSET), Base64.getDecoder().decode(sign), publicKey);
    }

    /*************************** 加密、加签、验签、解密 方法end ******************************/


    /**
     * 随机生成RSA密钥对(默认密钥长度为1024)
     */
    public static KeyPair generateRSAKeyPair() {
        return generateRSAKeyPair(keylength);
    }

    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength 密钥长度，范围：512～2048<br> 一般1024
     */
    public static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getKeySize(PublicKey publicKey) {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        return rsaPublicKey.getModulus().bitLength();
    }

    public static int getKeySize(PrivateKey privateKey) {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        return rsaPrivateKey.getModulus().bitLength();
    }

    /**
     * 用公钥加密 <br>
     * 每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param data      需加密数据的byte数据
     * @param publicKey 公钥
     * @return 加密后的byte型数据
     */
    public static byte[] encryptData(byte[] data, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            int keyBit = getKeySize(publicKey);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            int step = keyBit / 8 - 11;

            for (int i = 0; inputLen - offSet > 0; offSet = i * step) {
                byte[] cache;
                if (inputLen - offSet > step) {
                    cache = cipher.doFinal(data, offSet, step);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }

                out.write(cache, 0, cache.length);
                ++i;
            }

            byte[] encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] encryptByPublicKey(byte[] content, PublicKey publicKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    /**
     * 用私钥解密
     *
     * @param encryptedData 经过encryptedData()加密返回的byte数据
     * @param privateKey    私钥
     */
    public static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            int keyBit = getKeySize(privateKey);
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            int step = keyBit / 8;

            for (int i = 0; inputLen - offSet > 0; offSet = i * step) {
                byte[] cache;
                if (inputLen - offSet > step) {
                    cache = cipher.doFinal(encryptedData, offSet, step);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }

                out.write(cache, 0, cache.length);
                ++i;
            }

            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过公钥byte[](publicKey.getEncoded())将公钥还原，适用于RSA算法
     */
    public static PublicKey getPublicKey(byte[] keyBytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 通过私钥byte[]将公钥还原，适用于RSA算法
     */
    public static PrivateKey getPrivateKey(byte[] keyBytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 使用N、e值还原公钥
     */
    public static PublicKey getPublicKey(String modulus, String publicExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        BigInteger bigIntModulus = new BigInteger(modulus);
        BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 使用N、d值还原私钥
     */
    public static PrivateKey getPrivateKey(String modulus, String privateExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        BigInteger bigIntModulus = new BigInteger(modulus);
        BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(String publicKeyStr)
            throws Exception {
        try {
            byte[] buffer = Base64.getDecoder().decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从字符串中加载私钥<br>
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     */
    public static PrivateKey loadPrivateKey(String privateKeyStr)
            throws Exception {
        try {
            byte[] buffer = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(InputStream in)
            throws Exception {
        try {
            return loadPublicKey(readKey(in));
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param in 私钥文件名
     * @return 是否成功
     */
    public static PrivateKey loadPrivateKey(InputStream in)
            throws Exception {
        try {
            return loadPrivateKey(readKey(in));
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    /**
     * 读取密钥信息
     */
    private static String readKey(InputStream in)
            throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            if (readLine.charAt(0) == '-') {
                continue;
            } else {
                sb.append(readLine);
                sb.append('\r');
            }
        }

        return sb.toString();
    }

    /**
     * 使用私钥对数据进行加密签名
     *
     * @param encryptByte 数据
     * @param privateKey  私钥
     * @return 加密后的签名
     */
    public static String rsaSign(byte[] encryptByte, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(privateKey);
            signature.update(encryptByte);
            byte[] signed = signature.sign();
            return Base64.getEncoder().encodeToString(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用公钥判断签名是否与数据匹配
     *
     * @param encryptByte 数据
     * @param bs          签名
     * @param publicKey   公钥
     * @return 是否篡改了数据
     */
    public static boolean doCheck(byte[] encryptByte, byte[] bs, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(publicKey);
            signature.update(encryptByte);
            return signature.verify(bs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 打印公钥信息
     */
    public static void printPublicKeyInfo(PublicKey publicKey) {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        System.out.println("----------RSAPublicKey----------");
        System.out.println("Modulus.length=" + rsaPublicKey.getModulus().bitLength());
        System.out.println("Modulus=" + rsaPublicKey.getModulus().toString());
        System.out.println("PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength());
        System.out.println("PublicExponent=" + rsaPublicKey.getPublicExponent().toString());
    }

    public static void printPrivateKeyInfo(PrivateKey privateKey) {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        System.out.println("----------RSAPrivateKey ----------");
        System.out.println("Modulus.length=" + rsaPrivateKey.getModulus().bitLength());
        System.out.println("Modulus=" + rsaPrivateKey.getModulus().toString());
        System.out.println("PrivateExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength());
        System.out.println("PrivatecExponent=" + rsaPrivateKey.getPrivateExponent().toString());

    }

    @SuppressWarnings("restriction")
    public static String encryptBASE64(byte[] key)
            throws Exception {
        return Base64.getEncoder().encodeToString(key);
    }

    public static PublicKey getPublicKeyFromPem() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("D:\\项目\\svn\\portal\\trunk\\lib\\rsa_public_key.crt"));
        String s = br.readLine();
        String str = "";
        s = br.readLine();
        while (s.charAt(0) != '-') {
            str += s + "\r";
            s = br.readLine();
        }
        System.out.println(str);
        BASE64Decoder base64decoder = new BASE64Decoder();
        byte[] b = base64decoder.decodeBuffer(str);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(b);
        PublicKey pubKey = kf.generatePublic(keySpec);
        return pubKey;
    }

    public static PrivateKey getPrivateKeyForPem() throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("D:\\home\\www\\xykj\\loan\\rsa_private_key.pem"));
        String s = br.readLine();
        String str = "";
        s = br.readLine();
        while (s.charAt(0) != '-') {
            str += s + "\r";
            s = br.readLine();
        }
        System.out.println(str);
        return null;
    }
/**
    public static void main(String[] args) {

//        KeyPair key = generateRSAKeyPair();
//        try {
//            System.out.println("-----BEGIN PUBLIC KEY-----");
//            System.out.println(encryptBASE64(key.getPublic().getEncoded()));
//            System.out.println("-----END PUBLIC KEY-----");
//
//
//            System.out.println("-----BEGIN PRIVATE KEY-----");
//            System.out.println(encryptBASE64(key.getPrivate().getEncoded()));
//            System.out.println("-----END PRIVATE KEY-----");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        try {
//            String str = RSAUtils.encryptData("root","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbXCG68gZEELu2CUmUl8rOsTtD1i3Al+FdXYA2zHBJvvy4ptkO2oEErVYiLB3hZGRNJVU7Sk2rFbj3GYdfAYdDjEFRqFlM2EO4lxKgASs2T4VxVf5VV/nKKPuc2cEW+6OiZ0+SX30UlJ2OHeNk/6y2YttN398iANhV9zF1ECKOHQIDAQAB");
//            System.out.println(str);
//            String privatekey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJtcIbryBkQQu7YJSZSXys6xO0PWLcCX4V1dgDbMcEm+/Lim2Q7agQStViIsHeFkZE0lVTtKTasVuPcZh18Bh0OMQVGoWUzYQ7iXEqABKzZPhXFV/lVX+coo+5zZwRb7o6JnT5JffRSUnY4d42T/rLZi203f3yIA2FX3MXUQIo4dAgMBAAECgYBXNCWyu3sM5Z+XlCr6/yDVV+H+N9BClH3pXDxFhgdZPZy5QN4BWmCk8W6xi7XR9/nc26G1wAVMjMrf2DKF/2Oeg8DDLYJnyp4JbNlPbebyZ+J7E9fDpapKaZsvjVv1DFkmoW0SgA8tVeX8AwgLYq3pnPU3Qtgtnq+HqWSJqHGvwQJBANvFhEHVAzyk2q/8VWoIhJv0vi7vEBq3dSyTOWVK2uigFzkGKFToeLV+Sk4WCdJ6N/mEUDeGFQDSUgXcO7ZyNbkCQQC0+Ggg9K7Xv73bQ+nrSH6Ela0GuLOy/qkUzzWI13qw1R6ELcz+Kuc+7UHFx9PioK7pLInUKqvxBoOYZLGjxE2FAkAfm+8D0PFgPY2+lhpq38LC85aPBY9ZaF2QBbTRnsyRfMUaTIpse+swn3Nse7r57N40IxWuhrhqn9VwRlbITPDxAkA3vkh81kHBDTvS/XFhWvw1pNEeP+iCWzUDxuvLkewl6g+zUVRSei6u0HAxFuNqbGpUQrbV1qsN3B7O4z1gn4ftAkEAugrhmh38OyslDQJzBfhUK5u6GefjsqnYm2R0JJqW1erzsparBrL1Tps759MmoIZziSJa/q4JkItj2axtGT/gpQ==";
//            System.out.println(RSAUtils.decryptData(str,privatekey));
//        } catch (Exception e) {
//                e.printStackTrace();
//        }
//        try {
//            byte[] data = encryptByPublicKey("root".getBytes(),getPublicKeyFromPem());
//            String d = new String(Base64.getEncoder().encode(data), CHARSET);
//            System.out.println(d);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        System.out.println(IdUtil.objectId());

//        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        // application.properties, jasypt.encryptor.password
//        encryptor.setPassword("xykj");

//        System.out.println(encryptor.encrypt("root"));
    }
*/
}
