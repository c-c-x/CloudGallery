package com.CloudGallery.common.exception;

import java.io.Serial;

/**
 * 自定义业务异常类（继承 RuntimeException，无需显式捕获）
 */
public class CgServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public CgServiceException() {
    }

    public CgServiceException(String message) {
        this.message = message;
    }

    public CgServiceException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public CgServiceException setMessage(String message) {
        this.message = message;
        return this;
    }

    public CgServiceException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }

}