package com.example.transaction.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 加密工具类
 */
public class EncryptUtil {
    private static final Logger logger = Logger.getLogger(EncryptUtil.class);

    /**
     * 使用SHA-1算法计算摘要
     * @param content 待加密内容
     * @return 40位十六进制字符串
     */
    public static String encryptSHA1(String content) {
        try {
            if (StringUtils.isEmpty(content)) {
                logger.error("加密明文不能为空");
            }
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] result = md.digest(content.getBytes(StandardCharsets.UTF_8));
            return ConvertUtil.byteToHexString(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 使用SHA-256算法计算摘要
     * @param content 待加密内容
     * @return 64位十六进制字符串
     */
    public static String encryptSHA256(String content) {
        try {
            if (StringUtils.isEmpty(content)) {
                logger.error("加密明文不能为空");
            }
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] result = md.digest(content.getBytes(StandardCharsets.UTF_8));
            return ConvertUtil.byteToHexString(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
