package com.cloudgallery.common.utils;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * 数据脱敏工具类（仅支持 AES-GCM 认证加密模式）
 */
public class DesensitizationUtils {
    // 加密模式（固定为 AES-GCM）
    private static final String ENCRYPT_MODE = "AES/GCM/NoPadding";
    // 密钥长度（推荐 256 位，需 JCE 策略文件支持）
    private static final int KEY_LENGTH = 256;
    // PBKDF2 迭代次数（增强密钥安全性）
    private static final int PBKDF2_ITERATIONS = 65536;
    // 盐值长度（用于密钥派生）
    private static final int SALT_LENGTH = 16;
    // GCM 认证标签长度（128 位）
    private static final int GCM_TAG_LENGTH = 128;
    // IV 长度（GCM 推荐 12 字节）
    private static final int IV_LENGTH = 12;
    //
    private static final String password = "1767995016!";
    // ====================== 核心加密解密方法 ======================

    /**
     * AES-GCM 加密（自动生成 IV 和盐值）
     * @param plainText 明文内容
     * @return 加密结果（格式：Base64(盐值 + IV + 密文)）
     */
    public static String encrypt(String plainText) throws Exception {
        byte[] salt = generateSalt();          // 生成随机盐值
        SecretKey key = deriveKey(password, salt);  // 从密码派生密钥
        byte[] iv = generateIV();              // 生成随机 IV（12 字节）
        byte[] cipherText = doEncrypt(plainText.getBytes(StandardCharsets.UTF_8), key, iv); // 执行加密
        return encodeCombined(salt, iv, cipherText); // 拼接并编码
    }

    /**
     * AES-GCM 解密（从加密结果中提取盐值、IV 和密文）
     * @param encryptedText 加密结果（格式：Base64(盐值 + IV + 密文)）
     * @return 明文内容
     */
    public static String decrypt(String encryptedText) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedText);
        byte[] salt = extractBytes(combined, 0, SALT_LENGTH);       // 提取盐值（16 字节）
        byte[] iv = extractBytes(combined, SALT_LENGTH, IV_LENGTH); // 提取 IV（12 字节）
        byte[] cipherText = extractBytes(combined, SALT_LENGTH + IV_LENGTH,
                combined.length - SALT_LENGTH - IV_LENGTH); // 提取密文

        SecretKey key = deriveKey(password, salt); // 从密码派生密钥
        byte[] plainBytes = doDecrypt(cipherText, key, iv); // 执行解密
        return new String(plainBytes, StandardCharsets.UTF_8);
    }


    // ====================== 底层加密解密逻辑 ======================

    /**
     * 执行加密（GCM 模式）
     */
    private static byte[] doEncrypt(byte[] plainBytes, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPT_MODE);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);
        return cipher.doFinal(plainBytes);
    }

    /**
     * 执行解密（GCM 模式）
     */
    private static byte[] doDecrypt(byte[] cipherBytes, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPT_MODE);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);
        return cipher.doFinal(cipherBytes);
    }


    // ====================== 密钥与参数生成 ======================

    /**
     * 从密码派生密钥（PBKDF2 算法）
     */
    private static SecretKey deriveKey(String password, byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, PBKDF2_ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    /**
     * 生成随机盐值（用于密钥派生）
     */
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * 生成随机 IV（GCM 模式固定 12 字节）
     */
    private static byte[] generateIV() {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[IV_LENGTH];
        random.nextBytes(iv);
        return iv;
    }


    // ====================== 辅助工具方法 ======================

    /**
     * 拼接盐值、IV、密文并 Base64 编码
     */
    private static String encodeCombined(byte[] salt, byte[] iv, byte[] cipherText) {
        byte[] combined = new byte[salt.length + iv.length + cipherText.length];
        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(iv, 0, combined, salt.length, iv.length);
        System.arraycopy(cipherText, 0, combined, salt.length + iv.length, cipherText.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    /**
     * 从组合字节数组中提取指定部分
     */
    private static byte[] extractBytes(byte[] combined, int offset, int length) {
        byte[] result = new byte[length];
        System.arraycopy(combined, offset, result, 0, length);
        return result;
    }


    // ====================== 示例用法 ======================
    public static void main(String[] args) throws Exception {
//        String plainText = "这是一段需要加密的敏感数据，比如用户密码或支付信息。";
//
//        // GCM 模式加密
//        String encryptedGCM = DesensitizationUtils.encrypt(plainText);
//        System.out.println("GCM 加密结果: " + encryptedGCM);

        // GCM 模式解密
        String decryptedGCM = DesensitizationUtils.decrypt("6mlJWshmbIkbbbPQv0RJg8EgnKSJxDMjpnFFlAO5KnfHmyUb3NvcTgB9CyTOU9asKB7NB1BA");
        System.out.println("GCM 解密结果: " + decryptedGCM);
    }

}