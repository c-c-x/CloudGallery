package com.CloudGallery.common.response;

import com.CloudGallery.common.enums.ResultEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result<T> {
    /**
     * 响应码
     */
    private Integer code ;
    /**
     * 响应信息
     */
    private String message ;
    /**
     * 响应数据
     */
    private T data;

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, T data, String message) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    //  成功
    public static<T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(ResultEnums.SUCCESS.code());
        result.setMessage(ResultEnums.SUCCESS.message());
        result.setData(data);
        return result;
    }

    //  成功
    public static<T> Result<T> success(String message, T data) {
        Result<T> result = new Result<T>();
        result.setCode(ResultEnums.SUCCESS.code());
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    //  成功
    public static<T> Result<T> success(Integer code, String message, T data) {
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    //  成功
    public static<T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.setCode(ResultEnums.SUCCESS.code());
        result.setMessage(ResultEnums.SUCCESS.message());
        return result;
    }

    //  成功
    public static <T> Result<T> success(ResultEnums resultEnums) {
        Result<T> result = new Result<T>();
        result.code = resultEnums.getCode();
        result.message = resultEnums.getMessage();
        return result;
    }

    //  失败
    public static<T> Result<T> fail(Integer code, String message) {
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    //  失败
    public static<T> Result<T> fail(ResultEnums resultEnums) {
        Result<T> result = new Result<T>();
        result.code = resultEnums.getCode();
        result.message = resultEnums.getMessage();
        return result;
    }

    //  失败
    public static<T> Result<T> fail(String message) {
        Result<T> result = new Result<T>();
        result.setCode(ResultEnums.INTERNAL_SERVER_ERROR.code());
        result.setMessage(message);
        return result;
    }

    //  失败
    public static<T> Result<T> fail() {
        Result<T> result = new Result<T>();
        result.setCode(ResultEnums.INTERNAL_SERVER_ERROR.code());
        result.setMessage(ResultEnums.INTERNAL_SERVER_ERROR.message());
        return result;
    }

    //  失败
    public static<T> Result<T> fail(Integer code, String message, T detail) {
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(detail);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Result.success());
    }
}
