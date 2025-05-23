package com.CloudGallery.common.enums;

public enum ResultEnums {

    SUCCESS(200, "操作成功"),
    FAIL(400, "操作失败"),
    UNAUTHORIZED(401, "用户认证失败"),
    FORBIDDEN(403, "用户无权限"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_TIMEOUT(504, "网关超时");

    /**
     *  状态码
     */
    int code;
    /**
     *  描述
     */
    String message;

    ResultEnums(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
