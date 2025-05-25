package com.CloudGallery.common.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 用户工具类
 */
public class UserUtils {
    private UserUtils() {
        // 私有构造函数，防止实例化
    }

    /**
     * @return 用户 ID（未登录时返回 null）
     */
    public static Long getUserId() {
      return BaseContext.getCurrentUser().getId();
    }

    /**
     * 手机号单独脱敏（中国大陆手机号）
     * @param originalPhone 原始手机号（如 "13812345678"）
     * @return 脱敏后手机号（如 "138****5678"），空值/格式错误返回原始值
     */
    public static String maskPhone(String originalPhone) {
        // 空值处理
        if (StringUtils.isBlank(originalPhone)) {
            return originalPhone;
        }
        // 格式校验（11位数字，1开头，第二位3-9）
        if (!Pattern.matches("^1[3-9]\\d{9}$", originalPhone)) {
            return originalPhone;
        }
        // 脱敏规则：保留前3位和后4位，中间替换为****
        return originalPhone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 邮箱单独脱敏
     * @param originalEmail 原始邮箱（如 "zhangsan@example.com"）
     * @return 脱敏后邮箱（如 "z******n@example.com"），空值/格式错误返回原始值
     */
    public static String maskEmail(String originalEmail) {
        // 空值处理
        if (StringUtils.isBlank(originalEmail)) {
            return originalEmail;
        }
        // 格式校验（简化版邮箱正则）
        if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", originalEmail)) {
            return originalEmail;
        }
        // 提取用户名和域名（@前为用户名）
        int atIndex = originalEmail.indexOf('@');
        String username = originalEmail.substring(0, atIndex);
        String domain = originalEmail.substring(atIndex);
        // 用户名过短（<2位）不脱敏（如 "a@example.com"）
        if (username.length() < 2) {
            return originalEmail;
        }
        // 脱敏规则：保留用户名首尾字符，中间用*填充
        return username.charAt(0)
                + StringUtils.repeat('*', username.length() - 2)
                + username.charAt(username.length() - 1)
                + domain;
    }

    /**
     * 身份证号单独脱敏（支持15位和18位）
     * @param originalIdNumber 原始身份证号（如 "440101199001011234"）
     * @return 脱敏后身份证号（如 "440101********1234"），空值/格式错误返回原始值
     */
    public static String maskIdNumber(String originalIdNumber) {
        // 空值处理
        if (StringUtils.isBlank(originalIdNumber)) {
            return originalIdNumber;
        }
        // 格式校验（15位或18位数字，18位最后一位可能是X）
        if (!Pattern.matches("^\\d{15}(\\d{2}[0-9Xx])?$", originalIdNumber)) {
            return originalIdNumber;
        }
        // 脱敏规则：15位保留前6位和后3位，18位保留前6位和后4位
        if (originalIdNumber.length() == 15) {
            return originalIdNumber.replaceAll("(\\d{6})\\d{6}(\\d{3})", "$1******$2");
        } else {
            return originalIdNumber.replaceAll("(\\d{6})\\d{8}(\\d{4})", "$1********$2");
        }
    }
}
