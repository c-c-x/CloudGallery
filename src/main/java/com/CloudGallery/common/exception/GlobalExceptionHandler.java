package com.CloudGallery.common.exception;

import com.CloudGallery.common.response.Result;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局异常处理类（处理参数校验、业务异常等）
 */
@RestControllerAdvice
public class GlobalExceptionHandler   {

    /**
     * 处理参数校验失败异常（@Valid/@Validated 触发）
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 返回 HTTP 400 状态码
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        // 提取校验失败的字段错误信息
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        // 格式化为 {字段名: 错误信息} 的 Map
        Map<String, String> errorMap = fieldErrors.stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null
                                ? fieldError.getDefaultMessage()
                                : "字段校验失败" // 默认提示（防止 message 未设置）
                ));

        // 返回统一结果（包含错误信息）
        return Result.fail(
                HttpStatus.BAD_REQUEST.value(), 
                "参数校验失败", 
                errorMap
        );
    }

    /**
     * 处理自定义业务异常 CgServiceException
     */
    @ExceptionHandler(CgServiceException.class)
    public Result handleCgServiceException(CgServiceException ex, WebRequest request) {
        return Result.builder()
                .code(ex.getCode() != null ? ex.getCode() : 500) // 默认使用500状态码
                .message(ex.getMessage())
                .build();
    }
}